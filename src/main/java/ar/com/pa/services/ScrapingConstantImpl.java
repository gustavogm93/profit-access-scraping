package ar.com.pa.services;
//TODO Restructurar los nombres de Scrapping de todos

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import com.google.common.base.Function;
import com.google.common.base.Predicate;

import java.util.stream.Collectors;

import org.checkerframework.checker.nullness.qual.Nullable;
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
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import ar.com.pa.enums.RegionConstant;

import ar.com.pa.model.dto.CountryDTO;
import ar.com.pa.model.dto.MarketIndexDTO;
import ar.com.pa.model.dto.RegionDTO;
import ar.com.pa.model.props.CountryProps;
import ar.com.pa.model.props.CountryProps.Builder;
import ar.com.pa.model.props.MainProps;
import ar.com.pa.model.props.RegionProps;
import ar.com.pa.repository.RegionRepository;
import ar.com.pa.utils.SeleniumDriver;
import ar.com.pa.utils.SerializeImpl;
import io.vavr.control.Try;

@Component
public class ScrapingConstantImpl {

	private static final Logger logger = LoggerFactory.getLogger(ScrapingConstantImpl.class);

	private RegionRepository regionRepository;

	private SeleniumDriver seleniumDriver;

	final static ImmutableList<RegionConstant> regions = RegionConstant.values;

	// TODO regions and countrys collections
	// TODO element collections
	final static String urlWeb = "https://www.investing.com/equities/";

	@Autowired
	public ScrapingConstantImpl(RegionRepository regionRepository, SeleniumDriver seleniumDriver) {
		this.regionRepository = regionRepository;
		this.seleniumDriver = seleniumDriver;
	};

	public void getCountryConstant() {
		logger.info("Getting countries by regions");

		Try<Document> doc = Try.of(() -> Jsoup.connect(urlWeb).get());

		doc.onSuccess(data -> {

			regions.stream().forEach((region) -> saveRegionDTO(data, region));

		});

		doc.onFailure(ex -> {
			logger.error(ex.toString());
		});

	}

	public void saveRegionDTO(Document data, RegionConstant regionConstant) {

		List<CountryProps> countries = getCountriesInsideDocument(data, regionConstant);

		RegionProps regionProps = new RegionProps.Builder().code(regionConstant.getCode())
														   .title(regionConstant.getTitle())
														   .build();

		RegionDTO regionDTO = new RegionDTO.Builder().props(regionProps)
													 .countries(countries)
													 .build();

		regionRepository.save(regionDTO);

	}

	public List<CountryProps> getCountriesInsideDocument(Document data, RegionConstant region) {

		var idElement = String.format("#cdregion%s", region.getCode());

		Elements regionElements = data.select(idElement).first().select("a");

		return FluentIterable.from(regionElements).filter(isCountryElement).transform(elementToCountryProps).toList();

	}

	private static final Predicate<Element> isCountryElement = element -> {

		var tagAttributeValue = element.attr("href");
		var tagTextValue = element.text();

		return (tagAttributeValue.matches("/equities/[a-zA-Z]") && tagTextValue.matches("Market Overview"));
	};

	private static final Function<Element, CountryProps> elementToCountryProps = new Function<Element, CountryProps>() {

		public CountryProps apply(Element element) {
			var tagAttributeValue = element.attr("href");
			var tagTextValue = element.text();

			var codeCountry = tagAttributeValue.substring(0, 8);
			var titleCountry = tagTextValue;

			return new CountryProps.Builder().code(codeCountry).title(titleCountry).build();
		}

	};

	public void saveMarketIndex() {
		// TODO GUARDAR EN MARKET TODOS LOS INDEX
		Try<Document> doc = Try.of(() -> Jsoup.connect(
				"https://www.investing.com/equities/StocksFilter?noconstruct=1&smlID=10141&sid=&tabletype=price&index_id=13317")
				.get());

		doc.onSuccess(data -> {

			Elements regionElements = data.select("#stocksFilter");

		});

	}

	/*
	 * public void name() {
	 * 
	 * WebDriver chromeDriver = seleniumDriver.get();
	 * 
	 * 
	 * 
	 * 
	 * 
	 * List<RegionDTO> regions = regionRepository.findAll();
	 * 
	 * RegionDTO region = regions.get(0);
	 * 
	 * List<Country> countries = region.getCountries();
	 * 
	 * chromeDriver.get(String.format("%s%s", urlWeb, countries.get(0)));
	 * 
	 * //Country For Save MarketIndex Inside Loop Country country = new Country();
	 * 
	 * String idCountry = chromeDriver.findElement(By.id("countryID")).getText();
	 * 
	 * Select select = (Select)chromeDriver.findElement(By.id("stocksFilter"));
	 * List<WebElement> a = select.getOptions(); List<MarketIndexDTO> marketIndexes
	 * = new ArrayList<>();
	 * 
	 * for (WebElement webElement : a) { MarketIndexDTO marketIndex = new
	 * MarketIndexDTO();
	 * 
	 * String idMarketIndex = webElement.getAttribute("id"); String titleMarketIndex
	 * = webElement.getText();
	 * 
	 * marketIndex.setId(idMarketIndex); marketIndex.setTitle(titleMarketIndex);
	 * marketIndex.setCountryCode(idCountry);
	 * 
	 * webElement.click();
	 * 
	 * seleniumDriver.wait(chromeDriver, "cross_rate_markets_stocks_1");
	 * 
	 * WebElement table =
	 * chromeDriver.findElement(By.id("cross_rate_markets_stocks_1"));
	 * 
	 * List<WebElement> shareElements =
	 * table.findElements(By.className("bold left noWrap elp plusIconTd"));
	 * 
	 * for (WebElement element : shareElements) { String shareTitle =
	 * element.findElement(By.tagName("a")).getText(); String shareId =
	 * element.findElement(By.tagName("span")).getAttribute("data.id");
	 * 
	 * System.out.println(shareTitle); System.out.println(shareId); }
	 * 
	 * }
	 * 
	 * 
	 * }
	 */

	public boolean checkisMarketOverview(String s) {
		return s.contains("Market Overview");
	}

	public void getMarketIndex(Document data) {

	}

	public void getStockIndex(Document data) {

	}

}
