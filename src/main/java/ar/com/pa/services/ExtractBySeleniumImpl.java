package ar.com.pa.services;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ar.com.pa.mapper.Mapper;
import ar.com.pa.enums.utils.Url;
import ar.com.pa.model.Property;
import ar.com.pa.model.dto.*;
import ar.com.pa.model.props.*;
import ar.com.pa.repository.*;
import ar.com.pa.utils.Msg;

@Component
public class ExtractBySeleniumImpl {

	private RegionRepository regionRepository;

	private ShareRepository shareRepository;

	private MarketIndexRepository marketIndexRepository;

	private CountryRepository countryRepository;

	private FailedRepository failedRepository;

	@Value("${chrome.driver}")
	private String chromeDriverPath;

	private static WebDriver driver;

	private static Wait<WebDriver> wait;

	@FindBy(how = How.ID, using = "countryID")
	private WebElement spanCountryId;

	@FindBy(how = How.ID, using = "cross_rate_markets_stocks_1")
	private WebElement tableMarket;

	@FindBy(how = How.ID, using = "stocksFilter")
	private WebElement selectMarketIndex;

	@FindBy(how = How.CSS, using = ".bold.left.noWrap.elp.plusIconTd")
	private List<WebElement> shareElements;

	private static final Logger logger = LoggerFactory.getLogger(ExtractBySeleniumImpl.class);

	private ExtractBySeleniumImpl(RegionRepository regionRepository, ShareRepository shareRepository,
			MarketIndexRepository marketIndexRepository, CountryRepository countryRepository,
			FailedRepository failedRepository) {
		this.regionRepository = regionRepository;
		this.shareRepository = shareRepository;
		this.marketIndexRepository = marketIndexRepository;
		this.countryRepository = countryRepository;
		this.failedRepository = failedRepository;
	}

	// SE EJECUTE
	public void executor() {

		logger.info(Msg.seleniumExecutor);

		List<RegionDTO> regions = checkIfExistFailedRegion() ? getFailedRegionDTO() : regionRepository.findAll();

		regions.parallelStream().limit(2).forEach(region -> {

			region.getCountries().stream().limit(1).forEach(country -> {

				try {
					startFetchingProcess(country, region.getProperties());
				} catch (WebDriverException e) {

					saveFailedRegionDTO(region, country);
					initializeDriver();
					logger.error(e.getMessage());

				}

			});
			driver.close();
		});

	}

	private void initializeDriver() {

		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		driver = new ChromeDriver();

		PageFactory.initElements(driver, this);

		wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(1200)).ignoring(org.openqa.selenium.NoSuchElementException.class);
	}

	private ExpectedCondition<WebElement> checkForTableShares() {

		return ExpectedConditions.visibilityOf(tableMarket);
	}

	private void startFetchingProcess(Country country, Region region) {

		initializeDriver();

		logger.info(Msg.compound(country, Msg.fetchingCountry));
		// Go to Web
		driver.get(String.format("%s%s", Url.equities, country.getCode()));

		// Wait for Load Page

		wait.until(checkForTableShares());

		// Get List MarketIndex
		TreeSet<MarketIndexDTO> MarketIndexDTOList = fetchMarketIndexes();

		saveCountryDTO(country, region, MarketIndexDTOList);
		saveMarketIndexDTO(MarketIndexDTOList);

	}

	private TreeSet<MarketIndexDTO> fetchMarketIndexes() {

		// Get Options inside Market Index Select
		List<WebElement> optionsMarketIndex = new Select(selectMarketIndex).getOptions();

		var idCountry = spanCountryId.getAttribute("value");

		TreeSet<MarketIndexDTO> MarketIndexDTOList = new TreeSet<MarketIndexDTO>(MarketIndexDTO.byTitle);

		for (WebElement webElement : optionsMarketIndex) {

			var idMarketIndex = webElement.getAttribute("id");
			var titleMarketIndex = webElement.getText();

			MarketIndex marketIndex = new MarketIndex(idMarketIndex, titleMarketIndex);

			webElement.click();
			wait.until(checkForTableShares());

			TreeSet<Share> shares = getSharesByElement();

			MarketIndexDTO marketIndexDTO = new MarketIndexDTO(idMarketIndex, idCountry, marketIndex, shares);

			MarketIndexDTOList.add(marketIndexDTO);

		}
		return MarketIndexDTOList;
	}

	private TreeSet<Share> getSharesByElement() {

		TreeSet<Share> shareList = new TreeSet<Share>(Property.byTitle);
		for (WebElement element : shareElements) {

			String shareTitle = element.findElement(By.tagName("a")).getText();
			String shareId = element.findElement(By.tagName("span")).getAttribute("data-id");

			Share share = new Share(shareId, shareTitle);
			shareList.add(share);

		}
		return shareList;
	}

	private void saveSharesDTO(TreeSet<Share> shares) {

		List<ShareDTO> shareDTOList = shares.stream().map(Mapper.shareToShareDTO).collect(Collectors.toList());

		shareRepository.saveAll(shareDTOList);
	}

	private void saveMarketIndexDTO(TreeSet<MarketIndexDTO> marketIndexDTO) {

		marketIndexRepository.saveAll(marketIndexDTO);

	}

	private void saveCountryDTO(Country country, Region region, TreeSet<MarketIndexDTO> marketIndexListDTO) {

		TreeSet<MarketIndex> marketIndexList = new TreeSet<MarketIndex>(Property.byTitle);
		for (MarketIndexDTO marketIndexDTO : marketIndexListDTO) {
			marketIndexList.add(marketIndexDTO.getPropierties());
		}

		CountryDTO countryDTO = new CountryDTO(country.getCode(), country, region, marketIndexList);
		countryRepository.save(countryDTO);

	}

	private boolean checkIfExistFailedRegion() {
		return failedRepository.count() > 0;
	}

	private void saveFailedRegionDTO(RegionDTO region, Country country) {
		Optional<FailedRegionDTO> failedToUpdate = failedRepository.findById(region.getId());

		if (failedToUpdate.isPresent()) {

			FailedRegionDTO failedRegionUpdate = failedToUpdate.get();

			failedRegionUpdate.getCountries().add(country);

			failedRepository.save(failedRegionUpdate);

		} else {

			Set<Country> countries = Set.of(country);

			FailedRegionDTO newFailedRegion = new FailedRegionDTO(region.getId(), region.getProperties(), countries);

			failedRepository.save(newFailedRegion);

		}

	}

	private List<RegionDTO> getFailedRegionDTO() {

		List<RegionDTO> regions = failedRepository.findAll().stream().map(Mapper.failedToRegion)
				.collect(Collectors.toList());

		return regions;

	}

}
