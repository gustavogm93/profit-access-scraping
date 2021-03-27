package ar.com.pa.services;
//TODO Restructurar los nombres de Scrapping de todos

import java.util.List;
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
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import ar.com.pa.enums.RegionConstant;
import ar.com.pa.enums.utils.Url;
import ar.com.pa.model.Property;
import ar.com.pa.model.dto.RegionDTO;
import ar.com.pa.model.props.Country;
import ar.com.pa.model.props.Region;
import ar.com.pa.repository.RegionRepository;
import io.vavr.control.Try;

@Component
public class ExtractByJsoupImpl {

	private static final Logger logger = LoggerFactory.getLogger(ExtractByJsoupImpl.class);

	private RegionRepository regionRepository;

	final static ImmutableList<RegionConstant> regions = RegionConstant.values;

	@Autowired
	public ExtractByJsoupImpl(RegionRepository regionRepository) {
		this.regionRepository = regionRepository;
	};

	public void fetchRegion() {
		logger.info("Getting regionDTO");

		Try<Document> document = Try.of(() -> Jsoup.connect(Url.equities).get());

		document.onSuccess(data -> {

			regions.parallelStream().forEach((region) -> saveRegionDTO(data, region));

			logger.info("Save regionDTO");
		});

		document.onFailure(ex -> {
			logger.error(ex.toString());
		});

	}

	public void saveRegionDTO(Document data, RegionConstant regionConstant) {

		List<Country> countries = getCountriesInsideDocument(data, regionConstant);

		Region regionProps = new Region(regionConstant.getCode(), regionConstant.getTitle());

		RegionDTO regionDTO = new RegionDTO(regionProps, countries);
		
		regionRepository.save(regionDTO);

	}
	
	public void getRegionDTO() {

		List<RegionDTO> regionDTO = regionRepository.findAll();
		
		System.out.println(regionDTO.toString());

	}
	

	public List<Country> getCountriesInsideDocument(Document data, RegionConstant region) {

		var idElement = String.format("#cdregion%s", region.getCode());

		Elements regionElements = data.select(idElement).first().select("a");

		return FluentIterable.from(regionElements).filter(isCountryElement).transform(elementToCountryProps).toList();

	}

	private static final Predicate<Element> isCountryElement = element -> {

		return (element.attr("href").contains("/equities/") && !element.text().contains("Market Overview"));
	};

	private static final Function<Element, Country> elementToCountryProps = new Function<Element, Country>() {

		public Country apply(Element element) {

			var codeCountry = element.attr("href").split("/")[2];
			var titleCountry = element.text();

			return new Country(codeCountry, titleCountry);				
		}

	};

	public void saveMarketIndex() {

		Try<Document> doc = Try.of(() -> Jsoup.connect(
				"https://www.investing.com/equities/StocksFilter?noconstruct=1&smlID=10141&sid=&tabletype=price&index_id=13317")
				.get());

		doc.onSuccess(data -> {

			Elements regionElements = data.select("#stocksFilter");

		});

	}


}
