package ar.com.pa.scraping;


import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import static ar.com.pa.generics.Mapper.*;
import com.google.common.collect.ImmutableList;
import ar.com.pa.collections.country.*;
import ar.com.pa.collections.coverage.CoverageCountry;
import ar.com.pa.collections.coverage.CoverageCountryService;
import ar.com.pa.collections.marketIndex.*;
import ar.com.pa.collections.region.*;
import ar.com.pa.collections.share.ShareProp;
import ar.com.pa.generics.Property;
import ar.com.pa.scraping.selenium.InvestmentEquityPage;
import ar.com.pa.utils.Msg;

@Component 
public class ScrapingCountryStrategy extends InvestmentEquityPage  {
	
	private RegionService regionService;

	private CountryService countryService;

	private CoverageCountryService coverageCountryService;

	private MarketIndexService marketIndexService;

	private static final Logger log = LoggerFactory.getLogger(ScrapingCountryStrategy.class);

	private ScrapingCountryStrategy(RegionService regionService, MarketIndexService marketIndexService,
		CountryService countryService, CoverageCountryService coverageCountryService) {
		this.regionService = regionService;
		this.marketIndexService = marketIndexService;
		this.countryService = countryService;
		this.coverageCountryService = coverageCountryService;
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
					closeSeleniumDriver();
					startSeleniumDriver();
					setUpSeleniumDriver();
				}

			});
			driver.close();
		});

	}

	public ImmutableList<RegionDTO> getConstantsToFetch(@Nullable String regionTitle) {
		
		if (Objects.isNull(regionTitle) || regionTitle.isEmpty()) {
			log.info("Getting list of all regions..");
			
			ImmutableList<RegionDTO> region = regionService.getAll().stream().filter(notHaveCoverage).collect(ImmutableList.toImmutableList());
			
			return region;
		}
		
			log.info(String.format("Getting %s",regionTitle));
			
			ImmutableList<RegionDTO> region = regionService.findByTitle(regionTitle).stream().filter(notHaveCoverage).collect(ImmutableList.toImmutableList());
			
			return region;
	}

	public static Predicate<RegionDTO> notHaveCoverage = (region) -> !region.isCovered();
	

	private void fetchProcess(CountryProp country, RegionProp region) throws Exception {

		goToCountryPage(country.getCode());
		
		List<MarketIndexDTO> MarketIndexList = fetchMarketIndexes(driver);

		buildAndSaveCountry(country, region, MarketIndexList);
		
		saveMarketIndexList(MarketIndexList);

	}

	
	private void saveMarketIndexList(List<MarketIndexDTO> MarketIndexList) {
		
		marketIndexService.addAll(MarketIndexList);
		
	}

	private List<MarketIndexDTO> fetchMarketIndexes(WebDriver driver) {

		List<MarketIndexDTO> MarketIndexDTOList = new ArrayList<>();

		var countryID = getCountryId();

		for (WebElement optionMarketIndex : getOptionsOfMarketIndex()) {
			log.info(Msg.fetchingMarketIndex);

			var idMarketIndex = getIdMarketIndex(optionMarketIndex);
			var titleMarketIndex = getTitleMarketIndex(optionMarketIndex);

			optionMarketIndex.click();
			wait.until(checkForTableShares());

			TreeSet<ShareProp> shares = getSharesByElement(driver);

			MarketIndexProp marketIndex = new MarketIndexProp(idMarketIndex, titleMarketIndex);
			MarketIndexDTO marketIndexDTO = new MarketIndexDTO(idMarketIndex, countryID, marketIndex, shares);

			MarketIndexDTOList.add(marketIndexDTO);

		}
		return MarketIndexDTOList;
	}

	private TreeSet<ShareProp> getSharesByElement(WebDriver driver) {
		log.info(Msg.fetchingShares);
		TreeSet<ShareProp> shareList = new TreeSet<ShareProp>(Property.byTitle);
		
		for (WebElement shareElement : getShareElements()) {

			String shareTitle = getShareTitle(shareElement);
			String shareId = getShareId(shareElement);

			ShareProp share = new ShareProp(shareId, shareTitle);
			shareList.add(share);

		}
		log.info(Msg.saveShares);
		return shareList;
	}

	private CountryProp getCountryToSave(CountryProp country, WebDriver driver) {
		var countryID = driver.findElement(By.id("countryID")).getAttribute("value");
		return new CountryProp(countryID, country.getTitle());
	}


	private Set<ShareProp> getSharesByCountry(List<MarketIndexDTO> marketIndexListDTO) throws Exception {
		
		Optional<MarketIndexDTO> marketIndexOfAllSharesByCountry = marketIndexListDTO.stream()
																					 .filter(isOverallMarketIndex)
																					 .findFirst();

		if (marketIndexOfAllSharesByCountry.isPresent()) {
			MarketIndexDTO marketIndex = marketIndexOfAllSharesByCountry.get();
			Set<ShareProp> sharesByCountry = marketIndex.getShares();
			return sharesByCountry;
		}

		throw new Exception();
	}
	
	private TreeSet<MarketIndexProp> getMarketIndexesByCountry(List<MarketIndexDTO> marketIndexListDTO) throws Exception {
		
		
		TreeSet<MarketIndexProp> marketIndexList = marketIndexListDTO.stream()
				 .map(MarketIndexDtoToMarketIndexProp)
				 .collect(Collectors.toCollection(()-> new TreeSet<MarketIndexProp>(MarketIndexProp.byTitle)));
		
		return marketIndexList;
	}
	
	public static Predicate<MarketIndexDTO> isOverallMarketIndex = (marketIndex) -> marketIndex.getPropierties()
			.getCode().equalsIgnoreCase("all");


	private void buildAndSaveCountry(CountryProp country, RegionProp region, List<MarketIndexDTO> MarketIndexList) throws Exception {
		
		CountryProp countryToAdd = getCountryToSave(country, driver);
		
	    TreeSet<MarketIndexProp> marketIndexes = getMarketIndexesByCountry(MarketIndexList);
		 
		Set<ShareProp> shares = getSharesByCountry(MarketIndexList);
		
		var countryId = countryToAdd.getCode();
		
		boolean isCovered = getCountryCoverage(country.getCode(), shares, marketIndexes);
		
		updateRegionCoverage(region, isCovered);
		
		CountryDTO newCountryDTO = new CountryDTO(countryId, countryToAdd, region, shares, marketIndexes, isCovered);
		
		countryService.add(newCountryDTO);
		
	}
	
	
	public boolean getCountryCoverage(String countryCode, Set<ShareProp> shares, TreeSet<MarketIndexProp> marketIndexes) {
		
		Optional<CoverageCountry> optionalCoverageCountry = coverageCountryService.findByCode(countryCode).stream().findFirst();
			
		if(optionalCoverageCountry.isPresent()) {
			CoverageCountry coverageCountry = optionalCoverageCountry.get();
			
			if(shares.size() < coverageCountry.getSharesTotalQuantity() * 0.95)
			return false;
			
			return true;
		}
		
		return false;

	}
	
	public void updateRegionCoverage(RegionProp region, boolean isCovered) {
		
		Optional<RegionDTO> optionalRegion = regionService.findByCode(region.getCode()).stream().findFirst();
		
		if(optionalRegion.isPresent()) {
			RegionDTO regionDTO = optionalRegion.get();
			regionDTO.setCovered(isCovered);
			
			regionService.add(regionDTO);
		}

	}
	
}
