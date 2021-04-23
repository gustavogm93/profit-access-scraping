package ar.com.pa.scraping.selenium;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import static ar.com.pa.generics.Mapper.*;
import com.google.common.collect.ImmutableList;

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
import ar.com.pa.enums.utils.Url;
import ar.com.pa.generics.Property;
import ar.com.pa.utils.Msg;

@Component 
public class InvestmentFetchCountry extends InvestmentEquityPage  {
	//InvestmentBuildSummaryCountry
	/**
	 * Services
	 */
	private RegionService regionService;

	private CountryService countryService;

	private MarketIndexService marketIndexService;

	/**
	 * WebElement Checker for wait
	 */
	@FindBy(how = How.ID, using = "cross_rate_markets_stocks_1")
	private WebElement tableMarket;

	private static final Logger log = LoggerFactory.getLogger(InvestmentFetchCountry.class);

	private InvestmentFetchCountry(RegionService regionService, MarketIndexService marketIndexService,
			CountryService countryService) {
		this.regionService = regionService;
		this.marketIndexService = marketIndexService;
		this.countryService = countryService;
	}

	public void executor(@Nullable String regionTitle) {

		
		startSeleniumDriver();
		setUpSeleniumDriver();

		ImmutableList<RegionDTO> regions = getConstantsToFetch(regionTitle);

		regions.stream().forEach(region -> {

			region.getCountries().stream().limit(1).forEach(country -> {

				try {
					fetchProcess(country, region.getProperties(), driver);
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


	@Override
	public void setUpSeleniumDriver() {
		PageFactory.initElements(driver, this);

		wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(TIMEOUT_MEDIUM))
				.pollingEvery(Duration.ofMillis(POLLING_MEDIUM))
				.ignoring(org.openqa.selenium.NoSuchElementException.class);
	}

	public ImmutableList<RegionDTO> getConstantsToFetch(@Nullable String regionTitle) {
		
		if (Objects.isNull(regionTitle) || regionTitle.isEmpty()) {
			log.info("Getting list of all regions..");
			return ImmutableList.copyOf(regionService.getAll());
		}
		
			log.info(String.format("Getting %s",regionTitle));
			return ImmutableList.copyOf(regionService.findByTitle(regionTitle));

	}

	private ExpectedCondition<WebElement> checkForTableShares() {

		return ExpectedConditions.visibilityOf(tableMarket);
	}

	private void fetchProcess(CountryProp country, RegionProp region, WebDriver driver) throws Exception {

		driver.get(String.format("%s%s", Url.equities, country.getCode()));

		List<MarketIndexDTO> MarketIndexList = fetchMarketIndexes(driver);

		buildAndSaveCountry(country, region, MarketIndexList);
		
		saveMarketIndexList(MarketIndexList);

	}

	
	private void buildAndSaveCountry(CountryProp country, RegionProp region, List<MarketIndexDTO> MarketIndexList) throws Exception {
		
		CountryProp countryToAdd = getCountryToSave(country, driver);
		
	    TreeSet<MarketIndexProp> marketIndexes = getMarketIndexesByCountry(MarketIndexList);
		 
		Set<ShareProp> shares = getSharesByCountry(MarketIndexList);
		
		var countryId = countryToAdd.getCode();
		CountryDTO newCountryDTO = new CountryDTO(countryId, countryToAdd, region, shares, marketIndexes);
		
		countryService.add(newCountryDTO);
		
	}
	
	private void saveMarketIndexList(List<MarketIndexDTO> MarketIndexList) {
		
		marketIndexService.addAll(MarketIndexList);
		
	}

	// O(N) Process without refactoring
	private List<MarketIndexDTO> fetchMarketIndexes(WebDriver driver) {

		/** Create market index List to add and then return */
		List<MarketIndexDTO> MarketIndexDTOList = new ArrayList<>();

		/** Get id Country */
		var countryID = getCountryId();

		/** Get Select of Market Index */
		WebElement selectOfMarketIndex = driver.findElement(By.id("stocksFilter"));

		/** Get Options inside Select */
		List<WebElement> optionsMarketIndexes = new Select(selectOfMarketIndex).getOptions();

		/** Looping through options inside Select */
		for (WebElement optionMarketIndex : optionsMarketIndexes) {
			log.info(Msg.fetchingMarketIndex);

			/** Getting MarketIndex's id, title in Option of Select */
			var idMarketIndex = optionMarketIndex.getAttribute("id");
			var titleMarketIndex = optionMarketIndex.getText();

			/** Click on Option and wait for table */
			optionMarketIndex.click();
			wait.until(checkForTableShares());

			/** Get MarketIndex's shares inside Table */
			TreeSet<ShareProp> shares = getSharesByElement(driver);

			/** Create Market Index DTO */
			MarketIndexProp marketIndex = new MarketIndexProp(idMarketIndex, titleMarketIndex);
			MarketIndexDTO marketIndexDTO = new MarketIndexDTO(idMarketIndex, countryID, marketIndex, shares);

			MarketIndexDTOList.add(marketIndexDTO);

		}
		return MarketIndexDTOList;
	}



	// O(N) Get Shares for MarketIndex
	private TreeSet<ShareProp> getSharesByElement(WebDriver driver) {
		log.info(Msg.fetchingShares);
		TreeSet<ShareProp> shareList = new TreeSet<ShareProp>(Property.byTitle);
		List<WebElement> shareElements = driver.findElements(By.cssSelector(".bold.left.noWrap.elp.plusIconTd"));
		
		for (WebElement element : shareElements) {

			String shareTitle = element.findElement(By.tagName("a")).getText();
			String shareId = element.findElement(By.tagName("span")).getAttribute("data-id");

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



}
