package ar.com.pa.scraping;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import com.google.common.base.Strings;
import ar.com.pa.country.CountryProp;
import ar.com.pa.enums.utils.Url;
import ar.com.pa.marketIndex.MarketIndexProp;
import ar.com.pa.queue.SystemMessage;
import ar.com.pa.region.RegionDTO;
import ar.com.pa.region.RegionProp;
import ar.com.pa.region.RegionRepository;
import ar.com.pa.summary.SummaryCountryData;
import ar.com.pa.summary.SummaryMarketIndexData;
import ar.com.pa.summary.SummaryRegionDataDTO;
import ar.com.pa.utils.Msg;

@Component
public class GetSummaryRegionData implements SeleniumScraping{

	private RegionRepository regionRepository;

	private JmsTemplate jmsTemplate;

	@Value("${chrome.driver}")
	private String chromeDriverPath;

	@FindBy(how = How.ID, using = "countryID")
	private WebElement spanCountryId;

	@FindBy(how = How.ID, using = "cross_rate_markets_stocks_1")
	private WebElement tableMarket;

	@FindBy(how = How.ID, using = "stocksFilter")
	private WebElement selectMarketIndex;

	@FindBy(how = How.CSS, using = ".bold.left.noWrap.elp.plusIconTd")
	private List<WebElement> shareElements;

	private static WebDriver driver;

	private static Wait<WebDriver> wait;

	private static final Logger logger = LoggerFactory.getLogger(ExtractBySeleniumImpl.class);

	private GetSummaryRegionData(RegionRepository regionRepository, JmsTemplate jmsTemplate) {
		this.regionRepository = regionRepository;
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public void executor(String regionCode) {

		logger.info(Msg.executor);
		initializeDriver();

		List<RegionDTO> regions = regionRepository.findByTitle(regionCode);

		regions.stream().forEach(region -> {

			region.getCountries().stream().limit(5).forEach(country -> {

				try {
					startFetchingProcess(country, region.getProperties(), driver);
				} catch (Exception e) {
					logger.error(Msg.error(country, e.getMessage()));
					driver.quit();
					jmsTemplate.convertAndSend("region", new SystemMessage(country, region.getProperties()));
					initializeDriver();
				}

			});
			driver.close();
		});

	}
	
	@Override
	public void initializeDriver() {

		logger.info(Msg.startDriver);
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		driver = new ChromeDriver();

		PageFactory.initElements(driver, this);
		logger.info(Msg.startDriverSuccess);

		wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(withoutWait))
				.pollingEvery(Duration.ofMillis(pollingEvery)).ignoring(org.openqa.selenium.NoSuchElementException.class);

	}

	private ExpectedCondition<WebElement> checkForTableShares() {

		return ExpectedConditions.visibilityOf(tableMarket);
	}


	private void startFetchingProcess(CountryProp country, RegionProp region, WebDriver driver) throws Exception {

		// Go to Web
		driver.get(String.format("%s%s", Url.equities, country.getCode()));
		logger.info(Msg.compound(country, Msg.fetchingCountry));

		// Get List MarketIndex
		TreeSet<SummaryMarketIndexData> SummaryMarketIndexData = fetchSummaryMarketIndexes(driver);
		SummaryCountryData summaryCountryData = new SummaryCountryData(country, SummaryMarketIndexData);
		Set<SummaryCountryData> summaryCountrySet = Set.of(summaryCountryData);

		logger.info(Msg.compound(country, Msg.fetchingCountry));

		SummaryRegionDataDTO summaryRegionDataDTO = new SummaryRegionDataDTO(region, summaryCountrySet);
		
	}

	private TreeSet<SummaryMarketIndexData> fetchSummaryMarketIndexes(WebDriver driver) {

		// Get Options inside Market Index Select
		WebElement selectMarketIndex = driver.findElement(By.id("stocksFilter"));
		List<WebElement> optionsMarketIndex = new Select(selectMarketIndex).getOptions();

		logger.info(Msg.fetchingMarketIndex);

		TreeSet<SummaryMarketIndexData> summaryMarketIndexDataSet = new TreeSet<SummaryMarketIndexData>(
				SummaryMarketIndexData.byTitle);
		for (WebElement webElement : optionsMarketIndex) {

			var idMarketIndex = webElement.getAttribute("id");
			var titleMarketIndex = webElement.getText();

			MarketIndexProp marketIndex = new MarketIndexProp(idMarketIndex, titleMarketIndex);

			webElement.click();
			wait.until(checkForTableShares());

			Integer shareQuantity = getSharesQuantity(driver);
			SummaryMarketIndexData summaryMarketIndexDataUnit = new SummaryMarketIndexData(marketIndex, shareQuantity);

			summaryMarketIndexDataSet.add(summaryMarketIndexDataUnit);
		}
		return summaryMarketIndexDataSet;
	}

	private Integer getSharesQuantity(WebDriver driver) {
		Integer shareQuantity = 0;

		for (WebElement element : shareElements) {

			String shareTitle = element.findElement(By.tagName("a")).getText();
			String shareId = element.findElement(By.tagName("span")).getAttribute("data-id");

				if(checkSharesValidation(shareId, shareTitle))
				shareQuantity++;

		}

		return shareQuantity;
	}

	
	public boolean checkSharesValidation(String shareId, String shareTitle) {
		
		if(Strings.isNullOrEmpty( shareTitle))
			return false;
			
		if(Strings.isNullOrEmpty(shareId))
			return false;
		
		return true;
				
	}




}
