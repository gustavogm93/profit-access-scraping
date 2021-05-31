package ar.com.pa.scraping;

import ar.com.pa.collections.country.CountryProp;
import ar.com.pa.collections.region.RegionDTO;
import ar.com.pa.collections.region.RegionProp;
import ar.com.pa.collections.region.RegionService;
import ar.com.pa.enums.RegionConstant;
import ar.com.pa.enums.utils.Url;
import ar.com.pa.scraping.jsoup.JsoupBase;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
public class ScrapingRegionStrategy<element> implements JsoupBase {

	private static final Logger logger = LoggerFactory.getLogger(ScrapingRegionStrategy.class);
	private static final String urlEquities = Url.equities;
	
	private final RegionService regionService;

	@Autowired
	private ScrapingRegionStrategy(RegionService regionService) {
		this.regionService = regionService;
	}


	public void executor() throws IOException {
		logger.info("Getting regionDTO");

		Document document = getDocument(urlEquities);

		ImmutableList<RegionConstant> regions = getConstantsToFetch();

		regions.forEach((region) -> {

				try {

					Elements countriesElements = getCountriesElementsInDocument(document, region);

					Set<CountryProp> countries = elementsToCountrySet(countriesElements);

					RegionProp regionProps = new RegionProp(region.getCode(), region.getTitle());

					RegionDTO regionDTO = new RegionDTO(region.getCode(), regionProps, countries);

					regionService.add(regionDTO);
				} catch (Exception e) {
					logger.error(e.getMessage());
					throw e;
				}

			logger.info("Save regionDTO");
		});

	}


	public Document getDocument(String urlEquities) throws IOException {
		Document doc;
		try {
			doc = Jsoup.connect(urlEquities).get();
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return doc;
	}

	
	private ImmutableList<RegionConstant> getConstantsToFetch() {
		return RegionConstant.values;
	}

	private Elements getCountriesElementsInDocument(Document data, RegionConstant region) {
		
		String idElement = String.format("#cdregion%s", region.getCode());

		return  data.select(idElement)
				.first()
				.select("a");
	}
	
	private TreeSet<CountryProp> elementsToCountrySet(Elements elements) {
		
		return elements.stream()
				 .filter(isCountryElement)
				 .map(this::elementToCountryProps)
			     .collect(Collectors.toCollection(()-> new TreeSet<>(CountryProp.byCode)));
	}

	private final Predicate<Element> isCountryElement;
	{
		isCountryElement = (element) -> element.attr("href").contains("/equities/")
				&& !element.text().contains("Market Overview");
	}

	private Pair<String, String> getTitleAndCodeCountry (Element element){

			String titleCountry = element.text();
			String codeCountry = Objects.requireNonNull(element).attr("href").split("/")[2];

			return new CountryProp(codeCountry, titleCountry);
		}

	}




}
