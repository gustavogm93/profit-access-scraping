package ar.com.pa.scraping.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import com.google.common.collect.ImmutableList;
import static ar.com.pa.collections.coverage.VerifyCoverage.*;
import ar.com.pa.collections.country.CountryDTO;
import ar.com.pa.collections.country.CountryProp;
import ar.com.pa.collections.country.CountryService;
import ar.com.pa.collections.marketIndex.MarketIndexDTO;
import ar.com.pa.collections.marketIndex.MarketIndexProp;
import ar.com.pa.collections.marketIndex.MarketIndexService;
import ar.com.pa.collections.region.RegionDTO;
import ar.com.pa.collections.region.RegionProp;
import ar.com.pa.collections.region.RegionService;
import ar.com.pa.collections.share.ShareProp;
import ar.com.pa.collections.summary.ScrapedCoverageData;
import ar.com.pa.collections.summary.ScrapedData;
import ar.com.pa.collections.summary.ScrapingCountry;
import ar.com.pa.collections.summary.ScrapingCountryRepository;
import ar.com.pa.collections.summary.ScrapingCountryService;
import ar.com.pa.collections.summary.ScrapingRegion;
import ar.com.pa.collections.summary.ScrapingRegionService;
import ar.com.pa.enums.RegionConstant;
import ar.com.pa.enums.utils.Url;
import ar.com.pa.generics.Property;
import ar.com.pa.utils.Msg;
import lombok.NonNull;

import static ar.com.pa.generics.Mapper.*;

@Component
public class InvestingFetchCountry extends InvestingEquityPage {
	// InvestmentBuildSummaryCountry

	private RegionService regionService;

	private CountryService countryService;
	
	private final ScrapingCountryService scrapingCountryService;
	
	private final ScrapingRegionService scrapingRegionService;
	
	private MarketIndexService marketIndexService;
	
	private List<ScrapedData> marketIndexList;
	
	private List<ScrapedData> shareList;
	
	private static String countryId;
	
	private static final Logger log = LoggerFactory.getLogger(InvestingFetchCountry.class);

	private InvestingFetchCountry(RegionService regionService, MarketIndexService marketIndexService,
			CountryService countryService, ScrapingRegionService scrapingRegionService, ScrapingCountryService scrapingCountryService) {
		this.regionService = regionService;
		this.marketIndexService = marketIndexService;
		this.countryService = countryService;
		this.scrapingCountryService = scrapingCountryService;
		this.scrapingRegionService = scrapingRegionService;
		this.marketIndexList = new ArrayList<>();
		this.shareList = new ArrayList<>();
	}

	public void executor(@Nullable String regionTitle) {

		startSeleniumDriver();
		setUpSeleniumDriver();

		ImmutableList<RegionDTO> regions = getConstantsToFetch(regionTitle);
		regions.stream().forEach(region -> {

			region.getCountries().stream().limit(1).forEach(country -> {

				try {
					
					fetchProcess(country, region.getProperties());
					
				} catch (Exception e) {
					log.error(Msg.error(country, e.getMessage()));
					
					driver.quit();
					
					startSeleniumDriver();
					setUpSeleniumDriver();
				}

			});
			driver.close();
		});

	}

	public ImmutableList<RegionDTO> getConstantsToFetch(String regionTitle) {
		log.info(String.format("Getting %s", regionTitle));
		
		if(RegionConstant.isValidRegion(regionTitle)) {
			ScrapingRegion region = scrapingRegionService.findByTitle(regionTitle);
			
			 List<ScrapedCoverageData> countries = region.getCountries();
			 
			 List<ScrapedCoverageData> countriesWithOutCoverage = countries.stream().filter(withoutValidCoverage).collect(Collectors.toList());
			 
			List<ScrapingCountry> countries = scrapingCountryService.getAllCountriesWithoutCoverageByRegion(regionTitle);
			
			return null;
		}
		return null;
	}

	 
	
	
	private void fetchProcess(CountryProp country, RegionProp region) throws Exception {

		getPage(String.format("%s%s", Url.equities, country.getCode()));

		fetchMarketIndexes();

		buildSummaryScrapingCountry(country);

		saveSummaryScrapingCountry(MarketIndexList);

	}

	private void buildSummaryScrapingCountry(CountryProp country) {
		
		ScrapingCountry scrapingCountry = new ScrapingCountry();
		
	}

	private void saveMarketIndexList(List<MarketIndexDTO> MarketIndexList) {

		marketIndexService.addAll(MarketIndexList);

	}

	private void fetchMarketIndexes() {
		log.info(Msg.fetchingMarketIndex);

		countryId = getCountryId();

		List<WebElement> listOfoptionsMarketIndex = getMarketIndexOptions();

		for (WebElement optionMarketIndex : listOfoptionsMarketIndex) {
			log.info(Msg.fetchingMarketIndex);

			var idMarketIndex = getIdFromMarketIndexOption(optionMarketIndex);
			var titleMarketIndex = getTitleFromMarketIndexOption(optionMarketIndex);

			getSharesByElement(optionMarketIndex);

			ScrapedData marketIndex = new ScrapedData(idMarketIndex, titleMarketIndex);

			marketIndexList.add(marketIndex);

		}
		
	}

	private void getSharesByElement(WebElement optionMarketIndex) {
		
		log.info(Msg.fetchingShares);
		
		clickOnOption(optionMarketIndex);
		
		List<WebElement> shareElements = getSharesInTable();

		for (WebElement shareElement : shareElements) {

			String shareId = getShareId(shareElement);
			String shareTitle = getShareTitle(shareElement);
			
			ScrapedData share = new ScrapedData(shareId, shareTitle);
			shareList.add(share);

		}
		log.info(Msg.saveShares);
	}

	private CountryProp getCountryToSave(CountryProp country) {
		var countryID = getCountryId();
		return new CountryProp(countryID, country.getTitle());
	}

	private Set<ShareProp> getSharesByCountry(List<MarketIndexDTO> marketIndexListDTO) throws Exception {

		Optional<MarketIndexDTO> marketIndexOfAllSharesByCountry = marketIndexListDTO.stream()
				.filter(isOverallMarketIndex).findFirst();

		if (marketIndexOfAllSharesByCountry.isPresent()) {
			MarketIndexDTO marketIndex = marketIndexOfAllSharesByCountry.get();
			Set<ShareProp> sharesByCountry = marketIndex.getShares();
			return sharesByCountry;
		}

		throw new Exception();
	}

	public static Predicate<MarketIndexDTO> isOverallMarketIndex = (marketIndex) -> marketIndex.getPropierties()
			.getCode().equalsIgnoreCase("all");

}
