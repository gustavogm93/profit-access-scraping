package ar.com.pa.services;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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
import ar.com.pa.mapper.Mapper;
import ar.com.pa.enums.utils.Url;
import ar.com.pa.model.Property;
import ar.com.pa.model.constant.SummaryMarketIndexData;
import ar.com.pa.model.dto.*;
import ar.com.pa.model.props.*;
import ar.com.pa.model.queue.SystemMessage;
import ar.com.pa.repository.*;
import ar.com.pa.utils.Msg;

@Component
public class GetSummaryRegionDataImpl {

	private RegionRepository regionRepository;

	private ShareRepository shareRepository;

	private MarketIndexRepository marketIndexRepository;

	private CountryRepository countryRepository;

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

	private GetSummaryRegionDataImpl(RegionRepository regionRepository, ShareRepository shareRepository,
			MarketIndexRepository marketIndexRepository, CountryRepository countryRepository, JmsTemplate jmsTemplate) {
		this.regionRepository = regionRepository;
		this.shareRepository = shareRepository;
		this.marketIndexRepository = marketIndexRepository;
		this.countryRepository = countryRepository;
		this.jmsTemplate = jmsTemplate;
	}

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

	
	private void initializeDriver() {
		
		logger.info(Msg.startDriver);
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		driver = new ChromeDriver();

		PageFactory.initElements(driver, this);
		logger.info(Msg.startDriverSuccess);
		
		wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(6))
				.pollingEvery(Duration.ofMillis(1200)).ignoring(org.openqa.selenium.NoSuchElementException.class);
		
	}
	
	private ExpectedCondition<WebElement> checkForTableShares() {

		return ExpectedConditions.visibilityOf(tableMarket);
	}

	
	private void startFetchingProcess(Country country, Region region,WebDriver driver) throws Exception {
		
		// Go to Web
		driver.get(String.format("%s%s", Url.equities, country.getCode()));
		logger.info(Msg.compound(country, Msg.fetchingCountry));

		// Get List MarketIndex
		TreeSet<MarketIndexDTO> MarketIndexDTOList = fetchMarketIndexes(driver);
		
		logger.info(Msg.compound(country, Msg.fetchingCountry));
		saveCountryDTO(country, region, MarketIndexDTOList);
		
		logger.info(Msg.compound(country, Msg.fetchingCountry));
		saveMarketIndexDTO(MarketIndexDTOList);

	}

	private TreeSet<MarketIndexDTO> fetchMarketIndexes(WebDriver driver) {

		// Get Options inside Market Index Select
		WebElement selectMarketIndex = driver.findElement(By.id("stocksFilter"));

		List<WebElement> optionsMarketIndex = new Select(selectMarketIndex).getOptions();

		var idCountry = driver.findElement(By.id("countryID")).getAttribute("value");
		

		TreeSet<MarketIndexDTO> MarketIndexDTOList = new TreeSet<MarketIndexDTO>(MarketIndexDTO.byTitle);
		logger.info(Msg.fetchingMarketIndex);
		
		TreeSet<SummaryMarketIndexData> summaryMarketIndexDataSet =  new TreeSet<SummaryMarketIndexData>(SummaryMarketIndexData.byTitle);
		for (WebElement webElement : optionsMarketIndex) {

			var idMarketIndex = webElement.getAttribute("id");
			var titleMarketIndex = webElement.getText();
			
			MarketIndex marketIndex = new MarketIndex(idMarketIndex, titleMarketIndex);

			webElement.click();
			wait.until(checkForTableShares());
			
			Integer shareQuantity = getSharesQuantity(driver);
			SummaryMarketIndexData summaryMarketIndexDataUnit = new SummaryMarketIndexData(marketIndex, shareQuantity);
			

			summaryMarketIndexDataSet.add(summaryMarketIndexDataUnit);
		}
		return MarketIndexDTOList;
	}

	private Integer getSharesQuantity(WebDriver driver) {
		Integer shareQuantity = 0;
	
		for (WebElement element : shareElements) {

			String shareTitle = element.findElement(By.tagName("a")).getText();
			String shareId = element.findElement(By.tagName("span")).getAttribute("data-id");

			if(!(shareTitle.isEmpty() && shareId.isEmpty()))
				shareQuantity++;

		}

		return shareQuantity;
	}


	private void saveSharesDTO(TreeSet<Share> shares) {

		List<ShareDTO> shareDTOList = shares.stream().map(Mapper.shareToShareDTO).collect(Collectors.toList());

		shareRepository.saveAll(shareDTOList);
	}

	private void saveMarketIndexDTO(TreeSet<MarketIndexDTO> marketIndexDTO) {

		marketIndexRepository.saveAll(marketIndexDTO);

	}

	private void saveCountryDTO(Country country, Region region, TreeSet<MarketIndexDTO> marketIndexListDTO) throws Exception {

		TreeSet<MarketIndex> marketIndexList = new TreeSet<MarketIndex>(Property.byTitle);
		for (MarketIndexDTO marketIndexDTO : marketIndexListDTO) {
			marketIndexList.add(marketIndexDTO.getPropierties());
		}
		
		Optional<MarketIndexDTO> marketIndexOfAllSharesByCountry = marketIndexListDTO.stream().filter(isOverallMarketIndex).findFirst();
		
		if(marketIndexOfAllSharesByCountry.isPresent()) {
			MarketIndexDTO marketIndexGeneral = marketIndexOfAllSharesByCountry.get();
			Set<Share> allSharesByCountry = marketIndexGeneral.getShares();
			CountryDTO countryDTO = new CountryDTO(country.getCode(), country, region, allSharesByCountry, marketIndexList);
			countryRepository.save(countryDTO);
			return;
		}
		
		throw new Exception();

	}
	public static Predicate<MarketIndexDTO> isOverallMarketIndex = (marketIndex) -> marketIndex.getPropierties().getCode().equalsIgnoreCase("all");

}
