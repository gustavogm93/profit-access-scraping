package ar.com.pa.services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ar.com.pa.mapper.Mapper;
import ar.com.pa.enums.utils.Url;
import ar.com.pa.model.dto.CountryDTO;
import ar.com.pa.model.dto.FailedRegionDTO;
import ar.com.pa.model.dto.MarketIndexDTO;
import ar.com.pa.model.dto.RegionDTO;
import ar.com.pa.model.dto.ShareDTO;
import ar.com.pa.model.props.Country;
import ar.com.pa.model.props.MarketIndex;
import ar.com.pa.model.props.Region;
import ar.com.pa.model.props.Share;
import ar.com.pa.repository.CountryRepository;
import ar.com.pa.repository.FailedRepository;
import ar.com.pa.repository.MarketIndexRepository;
import ar.com.pa.repository.RegionRepository;
import ar.com.pa.repository.ShareRepository;
import ar.com.pa.utils.Msg;
import io.vavr.collection.Iterator;

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

	public void executor() {
		initializeDriver();
		logger.info(Msg.seleniumExecutor);

		List<RegionDTO> regions = checkIfExistFailedRegion() ? getFailedRegionDTO() : regionRepository.findAll();
		for (RegionDTO region : regions) {

			Set<Country> countries = region.getCountries();

			for (Country country : countries) {

				try {
					startFetchingProcess(country, region.getProperties());
				} catch (Exception e) {

					if (e instanceof WebDriverException exceptionChromeDriver) {
						saveFailedRegionDTO(region, country);
						initializeDriver();
						logger.error(exceptionChromeDriver.getMessage());
					}

				}
			}

		}
		driver.close();
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

		logger.info(Msg.compound(country, Msg.fetchingCountry));
		// Go to Web
		driver.get(String.format("%s%s", Url.equities, country.getCode()));

		// Wait for Load Page

		wait.until(checkForTableShares());

		// Get List MarketIndex
		List<MarketIndexDTO> MarketIndexDTOList = fetchMarketIndexes();

		saveCountryDTO(country, region, MarketIndexDTOList);
		saveMarketIndexDTO(MarketIndexDTOList);

	}

	private List<MarketIndexDTO> fetchMarketIndexes() {

		// Get Options inside Market Index Select
		List<WebElement> optionsMarketIndex = new Select(selectMarketIndex).getOptions();

		var idCountry = spanCountryId.getAttribute("value");

		List<MarketIndexDTO> MarketIndexDTOList = new ArrayList<>();

		for (WebElement webElement : optionsMarketIndex) {

			var idMarketIndex = webElement.getAttribute("id");
			var titleMarketIndex = webElement.getText();

			MarketIndex marketIndex = new MarketIndex(idMarketIndex, titleMarketIndex);

			webElement.click();
			wait.until(checkForTableShares());

			List<Share> shares = getSharesByElement();

			if (idMarketIndex.equalsIgnoreCase("all"))
				saveSharesDTO(shares);

			MarketIndexDTO marketIndexDTO = new MarketIndexDTO(idMarketIndex, idCountry, marketIndex, shares);

			MarketIndexDTOList.add(marketIndexDTO);

		}
		return MarketIndexDTOList;
	}

	private List<Share> getSharesByElement() {

		List<Share> shareList = new ArrayList<>();
		for (WebElement element : shareElements) {

			String shareTitle = element.findElement(By.tagName("a")).getText();
			String shareId = element.findElement(By.tagName("span")).getAttribute("data-id");

			Share share = new Share(shareId, shareTitle);
			shareList.add(share);

		}
		return shareList;
	}

	private void saveSharesDTO(List<Share> shares) {

		List<ShareDTO> shareDTOList = shares.stream().map(Mapper.shareToShareDTO).collect(Collectors.toList());

		shareRepository.saveAll(shareDTOList);
	}

	private void saveMarketIndexDTO(List<MarketIndexDTO> marketIndexDTO) {

		marketIndexRepository.saveAll(marketIndexDTO);

	}

	private void saveCountryDTO(Country country, Region region, List<MarketIndexDTO> marketIndexListDTO) {

		List<MarketIndex> marketIndexList = marketIndexListDTO.stream().map(MarketIndexDTO::getPropierties)
				.collect(Collectors.toList());

		CountryDTO countryDTO = new CountryDTO(country.getCode(), country, region, marketIndexList);
		countryRepository.save(countryDTO);
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

	private boolean checkIfExistFailedRegion() {
		return failedRepository.count() > 0;
	}

	private List<RegionDTO> getFailedRegionDTO() {

		List<RegionDTO> regions = failedRepository.findAll().stream().map(Mapper.failedToRegion)
				.collect(Collectors.toList());

		return regions;

	}

	public void testingPost() {
		Set<Country> list = new HashSet<>();

		Country country = new Country("200", "arg");
		list.add(country);

		Region region = new Region("1", "America");
		FailedRegionDTO failedDTO = new FailedRegionDTO("1", region, list);

		failedRepository.save(failedDTO);

		System.out.println("SAVED" + failedDTO);

	}

	public void testingGet() {
		FailedRegionDTO failedUpdate = failedRepository.findByRegionCode("1");

	}
}
