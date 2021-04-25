package ar.com.pa.scraping.selenium;

import java.util.List;
import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvestingEquityPage extends SeleniumBase {
	
	@FindBy(how = How.CSS, using = ".bold.left.noWrap.elp.plusIconTd")
	private List<WebElement> shareElements;
	
	@FindBy(how = How.ID, using = "cross_rate_markets_stocks_1")
	private WebElement tableShares;
	
	@FindBy(how = How.ID, using = "stocksFilter")
	private WebElement stocksFilter;

	@FindBy(how = How.ID, using = "countryID")
	private WebElement countrySpanId;

	private static final Logger log = LoggerFactory.getLogger(InvestingEquityPage.class);

	protected String getCountryId() {
		try {
			wait.until(checkInvisibilityOf(countrySpanId));
			return countrySpanId.getAttribute("value");
		} catch (WebDriverException e) {
			log.error("CountryId Element is not loaded", e.getMessage());
			throw e;
		}

	}

	protected List<WebElement> getMarketIndexOptions() throws NotFoundException {
		try {
			wait.until(checkVisibilityOf(stocksFilter));
			List<WebElement> optionsMarketIndex = buildSelect(stocksFilter).getOptions();
			if (optionsMarketIndex.size() == 0) {
				log.error("Options of MarketIndex in Share Table is empty");
				throw new NotFoundException();
			}
			return optionsMarketIndex;
		} catch (WebDriverException e) {
			log.error("Error has been ocurred while trying to get market index options", e.getMessage());
			throw e;
		}

	}

	protected String getIdFromMarketIndexOption(WebElement MarketIndexOptionWebElement) {
		try {
			return MarketIndexOptionWebElement.getAttribute("id");
		} catch (WebDriverException e) {
			log.error("Market index option element doesn't have value", e.getMessage());
			throw e;
		}

	}

	protected String getTitleFromMarketIndexOption(WebElement MarketIndexOptionWebElement) {
		try {
			return MarketIndexOptionWebElement.getText();
		} catch (WebDriverException e) {
			log.error("Market index option element doesn't have value", e.getMessage());
			throw e;
		}

	}

	protected void waitForSharesTable() {

		try {
			wait.until(checkVisibilityOf(tableShares));	
		} catch (WebDriverException e) {
			log.error("Table of shares Element is not loaded", e.getMessage());
			throw e;
		}

	}
	
	protected List<WebElement> getSharesInTable() {

		try {
			waitForSharesTable();
			if(Objects.isNull(shareElements) || shareElements.size() == 0) {
				log.error("Share Elements not Found in table");
				throw new NotFoundException();			
			}
				
			return shareElements;
		} catch (WebDriverException e) {
			log.error("Cannot get shares in Table ", e.getMessage());
			throw e;
		}

	}
	
	protected String getShareTitle(WebElement shareTitle) {

		try {
			return shareTitle.findElement(By.tagName("a")).getText();
		} catch (WebDriverException e) {
			log.error("Share title is not visible", e.getMessage());
			throw e;
		}

	}

	protected String getShareId(WebElement shareId) {

		try {
			return shareId.findElement(By.tagName("span")).getAttribute("data-id");
		} catch (WebDriverException e) {
			log.error("Share id is not visible\", ", e.getMessage());
			throw e;
		}

	}
	
	protected void clickOnOption(WebElement webElement) {
		webElement.click();		
	}

	
	
}
