package ar.com.pa.scraping;

import java.io.IOException;
import java.util.List;
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
import ar.com.pa.collections.summary.ScrapedCoverageData;
import ar.com.pa.collections.summary.ScrapedData;
import ar.com.pa.collections.summary.ScrapingRegion;
import ar.com.pa.collections.summary.ScrapingRegionService;
import ar.com.pa.enums.RegionConstant;
import ar.com.pa.enums.utils.Url;

@Service
public class InvestingFetchRegion implements JsoupScraping {

	private static final Logger logger = LoggerFactory.getLogger(InvestingFetchRegion.class);
	private static final String urlEquities = Url.equities;
	
	private ScrapingRegionService scrapingRegionService;

	@Autowired
	private InvestingFetchRegion(ScrapingRegionService scrapingRegionService) {
		this.scrapingRegionService = scrapingRegionService;
	};
	
	
	public void executor() throws IOException {
		logger.info("Getting regionDTO");

		Document document = getDocument(urlEquities);
		
		ImmutableList<RegionConstant> regions = getConstantsToFetch();
		
		regions.stream().forEach((region) -> {

				try {
					
					Elements countriesElements = getCountriesElementsInDocument(document, region);
					
					List<ScrapedCoverageData> countriesScrapedCoverageData = elementsToCountryScrapedCoverageData(countriesElements);
					
					ScrapedData regionScrapedData = new ScrapedData(region.getCode(), region.getTitle());
					
					ScrapingRegion scrapingRegion = new ScrapingRegion(region.getCode(), regionScrapedData, countriesScrapedCoverageData);
							
					scrapingRegionService.add(scrapingRegion);
					
				} catch (Exception e) {
					logger.error(e.getMessage());
					throw e;
				}

			logger.info("Save Scraped Regions");
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

	private Elements getCountriesElementsInDocument(Document data, RegionConstant region) {
		
		var idElement = String.format("#cdregion%s", region.getCode());

		Elements regionElements = data.select(idElement).first().select("a");
		
		return regionElements;
	}
	
	
	public List<ScrapedCoverageData> elementsToCountryScrapedCoverageData(Elements elements) {
		
		return elements.stream()
				 .filter(isCountryElement)
				 .map(elementToCountryScrapedData)
			     .collect(Collectors.toList());
		
	}

	private Predicate<Element> isCountryElement = (element) -> element.attr("href").contains("/equities/")
			&& !element.text().contains("Market Overview");

	
	private Function<Element, ScrapedCoverageData> elementToCountryScrapedData = new Function<Element, ScrapedCoverageData>() {

		public ScrapedCoverageData apply(Element element) {

			var codeCountry = element.attr("href").split("/")[2];
			var titleCountry = element.text();

			return new ScrapedCoverageData(codeCountry, titleCountry);
		}

	};







}
