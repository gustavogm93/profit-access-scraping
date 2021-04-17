package ar.com.pa.scraping;

import java.io.IOException;
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
import org.springframework.stereotype.Service;
import com.google.common.collect.ImmutableList;
import ar.com.pa.country.CountryProp;
import ar.com.pa.enums.RegionConstant;
import ar.com.pa.enums.utils.Url;
import ar.com.pa.region.RegionDTO;
import ar.com.pa.region.RegionProp;
import ar.com.pa.region.RegionService;

@Service
public class ScrapingRegionConstant implements JsoupScraping {

	private static final Logger logger = LoggerFactory.getLogger(ScrapingRegionConstant.class);
	private static final String urlEquities = Url.equities;
	
	private RegionService regionService;

	@Autowired
	private ScrapingRegionConstant(RegionService regionService) {
		this.regionService = regionService;
	};
	
	
	public void executor() throws IOException {
		logger.info("Getting regionDTO");

		Document document = getDocument(urlEquities);
		
		ImmutableList<RegionConstant> regions = getConstantsToFetch();
		
		regions.stream().forEach((region) -> {

				try {
					
					Elements countriesElements = getCountriesElementsInDocument(document, region);
					
					TreeSet<CountryProp> countries = elementsToTreeSet(countriesElements);
					
					RegionProp regionProps = new RegionProp(region.getCode(), region.getTitle());

					RegionDTO regionDTO = new RegionDTO(region.getCode(), regionProps, countries);
					
					regionService.addRegion(regionDTO);
				} catch (Exception e) {
					logger.error(e.getMessage());
					throw e;
				}

			logger.info("Save regionDTO");
		});

	}


	public Document getDocument(String urlEquities) throws IOException {
		Document doc = null;
		try {
			doc = Jsoup.connect(urlEquities).get();
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return doc;
	}
	
	
	public ImmutableList<RegionConstant> getConstantsToFetch() {
		return RegionConstant.values;
	}

	public Elements getCountriesElementsInDocument(Document data, RegionConstant region) {
		
		var idElement = String.format("#cdregion%s", region.getCode());

		Elements regionElements = data.select(idElement).first().select("a");
		
		return regionElements;
	}
	
	public TreeSet<CountryProp> elementsToTreeSet(Elements elements) {
		
		return elements.stream()
				 .filter(isCountryElement)
				 .map(elementToCountryProps)
			     .collect(Collectors.toCollection(()-> new TreeSet<CountryProp>(CountryProp.byCode)));
	}

	private Predicate<Element> isCountryElement = (element) -> element.attr("href").contains("/equities/")
			&& !element.text().contains("Market Overview");

	private Function<Element, CountryProp> elementToCountryProps = new Function<Element, CountryProp>() {

		public CountryProp apply(Element element) {

			var codeCountry = element.attr("href").split("/")[2];
			var titleCountry = element.text();

			return new CountryProp(codeCountry, titleCountry);
		}

	};








}
