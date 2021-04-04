package ar.com.pa.services;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
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
	public void executor(String regionCode) {

		logger.info(Msg.seleniumExecutor);
		
		WebDriver driver = getDriver();
		
		List<RegionDTO> regions = regionRepository.findByTitle(regionCode);

		regions.stream().forEach(region -> {

			region.getCountries().stream().limit(1).forEach(country -> {

				try {
					startFetchingProcess(country, region.getProperties(), driver);
				} catch (WebDriverException e) {

					saveFailedRegionDTO(region, country);
					logger.error(e.getMessage());

				}

			});
			driver.close();
		});
		
	}

	
	private WebDriver getDriver() {
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		WebDriver driver = new ChromeDriver();
		return driver;
	}
	
	private Wait<WebDriver> getWait(WebDriver driver) {
		return new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(1200)).ignoring(org.openqa.selenium.NoSuchElementException.class);
	}
	

	
	Function<WebDriver, List<WebElement>> expectedTable = new Function<WebDriver, List<WebElement>>() {
		  public List<WebElement> apply(WebDriver driver) {
		    return driver.findElements(By.id("cross_rate_markets_stocks_1"));
		  }
		};
	
	private ExpectedCondition<WebElement> checkForTableShares(Wait wait, WebDriver driver) {
		
		return ExpectedConditions.visibilityOf(driver.findElement(By.id("cross_rate_markets_stocks_1")));
	}

	private void startFetchingProcess(Country country, Region region,WebDriver driver) {
		
		logger.info(Msg.compound(country, Msg.fetchingCountry));
		// Go to Web
		driver.get(String.format("%s%s", Url.equities, country.getCode()));


		// Get List MarketIndex
		TreeSet<MarketIndexDTO> MarketIndexDTOList = fetchMarketIndexes(driver);

		saveCountryDTO(country, region, MarketIndexDTOList);
		saveMarketIndexDTO(MarketIndexDTOList);

	}

	private TreeSet<MarketIndexDTO> fetchMarketIndexes(WebDriver driver) {

		// Get Options inside Market Index Select
		WebElement selectMarketIndex = driver.findElement(By.id("stocksFilter"));

		List<WebElement> optionsMarketIndex = new Select(selectMarketIndex).getOptions();

		var idCountry = driver.findElement(By.id("countryID")).getAttribute("value");
		

		TreeSet<MarketIndexDTO> MarketIndexDTOList = new TreeSet<MarketIndexDTO>(MarketIndexDTO.byTitle);

		for (WebElement webElement : optionsMarketIndex) {

			var idMarketIndex = webElement.getAttribute("id");
			var titleMarketIndex = webElement.getText();

			MarketIndex marketIndex = new MarketIndex(idMarketIndex, titleMarketIndex);

			webElement.click();
			
			TreeSet<Share> shares = getSharesByElement(driver);

			MarketIndexDTO marketIndexDTO = new MarketIndexDTO(idMarketIndex, idCountry, marketIndex, shares);

			MarketIndexDTOList.add(marketIndexDTO);

		}
		return MarketIndexDTOList;
	}

	private TreeSet<Share> getSharesByElement(WebDriver driver) {

		TreeSet<Share> shareList = new TreeSet<Share>(Property.byTitle);
		
		//List<WebElement> shareElements = wait.until(expectedTable);
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(1200));
		List<WebElement> shareElements = wait.until(new Function<WebDriver, List<WebElement>>() {
			  public List<WebElement> apply(WebDriver driver) {
			    return driver.findElements(By.id("cross_rate_markets_stocks_1"));
			  }
			});
		
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
