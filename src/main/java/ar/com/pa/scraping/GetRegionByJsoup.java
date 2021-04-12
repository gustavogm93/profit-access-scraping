package ar.com.pa.scraping;

import static ar.com.pa.generics.Property.newTreeSet;

import java.util.TreeSet;
import java.util.stream.Collectors;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.common.collect.ImmutableList;

import Region.Region;
import Region.RegionRepository;
import ar.com.pa.country.Country;
import ar.com.pa.enums.RegionConstant;
import ar.com.pa.enums.utils.Url;
import ar.com.pa.model.dto.RegionDTO;
import io.vavr.control.Try;

@Component
public class GetRegionByJsoup {

	private static final Logger logger = LoggerFactory.getLogger(GetRegionByJsoup.class);
	private static final ImmutableList<RegionConstant> regions = RegionConstant.values;

	private RegionRepository regionRepository;

	@Autowired
	private GetRegionByJsoup(RegionRepository regionRepository) {
		this.regionRepository = regionRepository;
	};

	public void fetchRegion() {
		logger.info("Getting regionDTO");

		Try<Document> document = Try.of(() -> Jsoup.connect(Url.equities).get());

		document.onSuccess(data -> {

			regions.stream().forEach((region) -> saveRegionDTO(data, region));

			logger.info("Save regionDTO");
		});

		document.onFailure(ex -> {
			logger.error(ex.getMessage());
		});

	}


	
	private void saveRegionDTO(Document data, RegionConstant regionConstant) {

		TreeSet<Country> countries = getCountriesInsideDocument(data, regionConstant);

		Region regionProps = new Region(regionConstant.getCode(), regionConstant.getTitle());

		RegionDTO regionDTO = new RegionDTO(regionProps.getCode(), regionProps, countries);

		regionRepository.save(regionDTO);

	}
	

	private TreeSet<Country> getCountriesInsideDocument(Document data, RegionConstant region) {

		var idElement = String.format("#cdregion%s", region.getCode());

		Elements regionElements = data.select(idElement).first().select("a");

		return regionElements.stream().filter(isCountryElement).map(elementToCountryProps)
				.collect(Collectors.toCollection(newTreeSet));

	}

	private Predicate<Element> isCountryElement = (element) -> element.attr("href").contains("/equities/")
			&& !element.text().contains("Market Overview");

	private Function<Element, Country> elementToCountryProps = new Function<Element, Country>() {

		public Country apply(Element element) {

			var codeCountry = element.attr("href").split("/")[2];
			var titleCountry = element.text();

			return new Country(codeCountry, titleCountry);
		}

	};
}
