package ar.com.pa.services;
//TODO Restructurar los nombres de Scrapping de todos

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableList;
import ar.com.pa.enums.RegionConstant;
import ar.com.pa.model.Country;
import ar.com.pa.model.CountryDTO;
import ar.com.pa.model.MarketIndexDTO;
import ar.com.pa.model.RegionDTO;
import ar.com.pa.repository.RegionRepository;
import ar.com.pa.utils.SeleniumDriver;
import ar.com.pa.utils.SerializeImpl;
import io.vavr.control.Try;

@Component
public class ScrapingConstantImpl {

	private static final Logger logger = LoggerFactory.getLogger(ScrapingConstantImpl.class);

	SerializeImpl serializeImpl;

	RegionRepository regionRepository;
	
	final SeleniumDriver seleniumDriver;
	
	final static ImmutableList<RegionConstant> regions = RegionConstant.values;
	
	final static String urlWeb = "https://www.investing.com/equities/";
	
	@Autowired
	public ScrapingConstantImpl(SerializeImpl serializeImpl, RegionRepository regionRepository, SeleniumDriver seleniumDriver) {
		this.serializeImpl = serializeImpl;
		this.regionRepository = regionRepository;
		this.seleniumDriver = seleniumDriver;
	};

	public void getCountryConstant() {
		logger.info("Getting countries by regions");

		Try<Document> doc = Try.of(() -> Jsoup.connect(urlWeb).get());

		doc.onSuccess(data -> {

			regions.stream().forEach((region) -> saveCountriesByRegion(data, region));

		});

		doc.onFailure(ex -> {
			logger.error(ex.toString());
		});

	}

	public void saveCountriesByRegion(Document data, RegionConstant region) {

		RegionDTO regionDTO = new RegionDTO();
		List<CountryDTO> countries = getCountryList(data, region);
		
		regionDTO.setId(region.getCode());
		regionDTO.setTitle(region.getTitle());		
		regionDTO.setCountries(countries);
		
		regionRepository.save(regionDTO);
	}

	public List<CountryDTO> getCountryList(Document data, RegionConstant region) {

		String idElement = String.format("#cdregion%s", region.getCode());

		Elements regionElements = data.select(idElement).first().select("a");

		return regionElements.stream().filter(this::getCountryElement).map(this::convertToCountry).distinct()
				.collect(Collectors.toList());

	}



	private Boolean getCountryElement(Element element) {
		String tagAttributeValue = element.attr("href");
		String tagTextValue = element.text();

		if(Objects.isNull(element))
		return false;
		
		if(tagAttributeValue.contains("/equities/") && tagTextValue.contains("Market Overview"))
			return true;
		else
			return false;

	}
	
	private CountryDTO convertToCountry(Element element) {
		
		String tagAttributeValue = element.attr("href");
		String tagTextValue = element.text();
		
		String codeCountry = tagAttributeValue.substring(0,8);
		String titleCountry = tagTextValue;

		//TODO MODELED DE COUNTRY O COUNTRY DTO
		return new CountryDTO.Builder().code(codeCountry).title(titleCountry).build();

	}
	
	public void saveMarketIndex( ) {
		// TODO GUARDAR EN MARKET TODOS LOS INDEX
		Try<Document> doc = Try.of(() -> Jsoup.connect("https://www.investing.com/equities/StocksFilter?noconstruct=1&smlID=10141&sid=&tabletype=price&index_id=13317").get());

		doc.onSuccess(data -> {

			Elements regionElements = data.select("#stocksFilter");

		});	
		
	}
	
	public void dsd() {
		
		CountryDTO a = new CountryDTO.Builder().code("250").title("53").build();
		System.out.println(a);
		System.out.println(a);
	}

	
	public void name() {

		WebDriver chromeDriver = seleniumDriver.get();

		

		
		
		List<RegionDTO> regions = regionRepository.findAll();

		RegionDTO region = regions.get(0);

		List<Country> countries = region.getCountries();

		chromeDriver.get(String.format("%s%s", urlWeb, countries.get(0)));
		
		//Country For Save MarketIndex Inside Loop
		Country country = new Country();
				
		String idCountry = chromeDriver.findElement(By.id("countryID")).getText();
		
		Select select = (Select)chromeDriver.findElement(By.id("stocksFilter"));
		List<WebElement> a = select.getOptions();
		List<MarketIndexDTO> marketIndexes = new ArrayList<>(); 
		
		for (WebElement webElement : a) {
			MarketIndexDTO marketIndex = new MarketIndexDTO(); 
	
			String idMarketIndex = webElement.getAttribute("id");
			String titleMarketIndex = webElement.getText();
			
			marketIndex.setId(idMarketIndex);
			marketIndex.setTitle(titleMarketIndex);
			marketIndex.setCountryCode(idCountry);
			
			webElement.click();
			
			seleniumDriver.wait(chromeDriver, "cross_rate_markets_stocks_1");
			
			WebElement table = chromeDriver.findElement(By.id("cross_rate_markets_stocks_1"));
			
			List<WebElement> shareElements = table.findElements(By.className("bold left noWrap elp plusIconTd"));
			
			for (WebElement element : shareElements) {
				String shareTitle = element.findElement(By.tagName("a")).getText();
				String shareId = element.findElement(By.tagName("span")).getAttribute("data.id");
				
				System.out.println(shareTitle);
				System.out.println(shareId);
			}
			
		}
		
		
	}
	
	

	public boolean checkisMarketOverview(String s) {
		return s.contains("Market Overview");
	}

	public void getMarketIndex(Document data) {

	}

	public void getStockIndex(Document data) {

	}

}
