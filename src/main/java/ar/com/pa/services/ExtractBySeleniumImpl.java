package ar.com.pa.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

import ar.com.pa.enums.utils.Url;
import ar.com.pa.model.Property;
import ar.com.pa.model.dto.CountryDTO;
import ar.com.pa.model.dto.MarketIndexDTO;
import ar.com.pa.model.dto.RegionDTO;
import ar.com.pa.model.dto.ShareDTO;
import ar.com.pa.model.props.Country;
import ar.com.pa.model.props.MainProps;
import ar.com.pa.model.props.MarketIndex;
import ar.com.pa.model.props.Region;
import ar.com.pa.model.props.Share;
import ar.com.pa.repository.CountryRepository;
import ar.com.pa.repository.MarketIndexRepository;
import ar.com.pa.repository.RegionRepository;
import ar.com.pa.repository.ShareRepository;

@Component
public class ExtractBySeleniumImpl {

	@Value("${chrome.driver}")
	private String driverPath;

	private RegionRepository regionRepository;

	private ShareRepository shareRepository;

	private MarketIndexRepository marketIndexRepository;

	private CountryRepository countryRepository;

	private static WebDriver driver;

	@FindBy(how = How.ID, using = "countryID")
	private WebElement spanCountryId;

	@FindBy(how = How.ID, using = "cross_rate_markets_stocks_1")
	private WebElement tableMarket;

	@FindBy(how = How.ID, using = "stocksFilter")
	private WebElement selectMarketIndex;

	@FindBy(how = How.CSS, using = ".bold.left.noWrap.elp.plusIconTd")
	private List<WebElement> shareElements;

	public ExtractBySeleniumImpl(RegionRepository regionRepository, ShareRepository shareRepository,
			MarketIndexRepository marketIndexRepository, CountryRepository countryRepository) {
		this.regionRepository = regionRepository;
		this.shareRepository = shareRepository;
		this.marketIndexRepository = marketIndexRepository;
		this.countryRepository = countryRepository;
	}

	public void init() {

		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();

		PageFactory.initElements(driver, this);
	}

	private ExpectedCondition<WebElement> checkForTableShares() {

		return ExpectedConditions.visibilityOf(tableMarket);
	}

	public void name() {
		init();

		List<RegionDTO> regions = regionRepository.findAll();

		// region FOR EACH
		RegionDTO region = regions.get(0);

		Country countries = region.getCountries().get(0);

		getCountryAndMarketIndex(countries, region.getProperties());
	}

	public void getCountryAndMarketIndex(Country country, Region region) {

		// Go to Web
		driver.get(String.format("%s%s", Url.equities, country.getCode()));

		// Wait for Load Page
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(checkForTableShares());

		// Get MarketIndex Options
		List<WebElement> optionsMarketIndex = new Select(selectMarketIndex).getOptions();

		var idCountry = spanCountryId.getAttribute("value");
		// POR CADA OPCION EN EL SELECT

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

			MarketIndexDTO marketIndexDTO = getMarketIndexDTO(idCountry, marketIndex, shares);
			MarketIndexDTOList.add(marketIndexDTO);

		}
		List<MarketIndex> marketIndexList = MarketIndexDTOList.stream().map(MarketIndexDTO::getPropierties)
				.collect(Collectors.toList());
		saveCountryDTO(country, region, marketIndexList);

		marketIndexRepository.saveAll(MarketIndexDTOList);

	}

	public List<Share> getSharesByElement() {

		List<Share> shareList = new ArrayList<>();
		for (WebElement element : shareElements) {

			String shareTitle = element.findElement(By.tagName("a")).getText();
			String shareId = element.findElement(By.tagName("span")).getAttribute("data-id");

			Share share = new Share(shareId, shareTitle);
			shareList.add(share);

		}
		return shareList;
	}

	public void saveSharesDTO(List<Share> shares) {

		List<ShareDTO> shareDTOList = FluentIterable.from(shares).transform(ShareDTO::new).toList();

	}

	public MarketIndexDTO getMarketIndexDTO(String idCountry, MarketIndex marketIndex, List<Share> sharesIndividual) {

		MarketIndexDTO marketIndexDTO = new MarketIndexDTO(idCountry, marketIndex, sharesIndividual);

		return marketIndexDTO;
	}

	public void saveMarketIndexDTO(List<MarketIndexDTO> marketIndexDTO) {

		marketIndexRepository.saveAll(marketIndexDTO);

	}

	public void saveCountryDTO(Country country, Region region, List<MarketIndex> marketIndexList) {

		CountryDTO countryDTO = new CountryDTO(country, region, marketIndexList);
		countryRepository.save(countryDTO);
	}

	private static final Function<Share, ShareDTO> shareToDTO = new Function<Share, ShareDTO>() {

		public ShareDTO apply(Share share) {

			return new ShareDTO(share);
		}

	};

}
