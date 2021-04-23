package ar.com.pa.scraping.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import ar.com.pa.scraping.SeleniumBase;

public class InvestmentEquityPage extends SeleniumBase{

	
	@FindBy(how = How.ID, using = "cross_rate_markets_stocks_1")
	private WebElement tableMarket;
	
	
	protected String getCountryId() {
		wait(driver.findElement(By.id("countryID")));
		return driver.findElement(By.id("countryID")).getAttribute("value");		
	}
}
