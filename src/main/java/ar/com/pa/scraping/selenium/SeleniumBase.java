package ar.com.pa.scraping.selenium;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.pa.utils.Msg;


public class SeleniumBase  {

	protected WebDriver driver;

	protected URL chromeDriverPath;

	protected Wait<WebDriver> wait;

	protected int TIMEOUT_MEDIUM = 6;

	protected long POLLING_MEDIUM = 3;

	protected int IMPLICIT_MEDIUM = 5;

	protected final static String ChromeDriverPathResource = "driver/chromedriver.exe";

	private final static Logger log = LoggerFactory.getLogger(SeleniumBase.class);

	
	protected void startSeleniumDriver() {

		log.info(Msg.executor);

		String chromeDriverPath = SeleniumBase.class.getClassLoader().getResource(ChromeDriverPathResource)
				.getPath();

		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		driver = new ChromeDriver();

	}

	/**
	 * Method for Init Page's elements and fluent wait.
	 */
	protected void setUpSeleniumDriver() {
		PageFactory.initElements(driver, this);

		wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(TIMEOUT_MEDIUM))
				.pollingEvery(Duration.ofSeconds(POLLING_MEDIUM))
				.ignoring(org.openqa.selenium.NoSuchElementException.class);
	}
	
	protected void getPage(String url) {
		driver.get(url);
	}
	
	protected void changeImplicitWait(int value, TimeUnit timeUnit) {
		driver.manage().timeouts().implicitlyWait(value, timeUnit);
	}

	protected void restoreDefaultImplicitWait() {
		driver.manage().timeouts().implicitlyWait(TIMEOUT_MEDIUM, TimeUnit.SECONDS);
	}

	/**
	 * Simple method for checking if element is on page or not. Changing the
	 * implicitWait value allows us no need for waiting 30 seconds
	 */
	protected boolean isElementOnPage(By by) {
		changeImplicitWait(IMPLICIT_MEDIUM, TimeUnit.SECONDS);
		try {
			return driver.findElements(by).size() > 0;
		} finally {
			restoreDefaultImplicitWait();
		}
	}

	/**
	 * WebElement.isEnabled() method signature says that it returns true for
	 * anything except disabled input fields. In order to check if non-input
	 * elements are disabled, "disabled" attribute value must be checked and
	 * compared to "true" value
	 *
	 * @param element WebElement on the page
	 * @return true if value of "disabled" attribute is different than "true"
	 */
	protected boolean isElementEnabled(WebElement element) {
		return !"true".equals(element.getAttribute("disabled"));
	}

	/**
	 * Method to check if WebElement is displayed on the page
	 * @return true if element is displayed, otherwise return false
	 */
	protected boolean isElementDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			log.info(e.getMessage());
			return false;
		}
	}
	
	  /**
	   * An expectation for checking that an element, known to be present on the DOM of a page, is
	   * visible. Visibility means that the element is not only displayed but also has a height and
	   * width that is greater than 0.
	   *
	   * @param element the WebElement
	   * @return the (same) WebElement once it is visible
	   */
	protected ExpectedCondition<WebElement> checkVisibilityOf(WebElement webElement) {

		return ExpectedConditions.visibilityOf(webElement);
	}
	
	protected ExpectedCondition<List<WebElement>> checkVisibilityOfAllElements(List<WebElement> webElements) {

		return ExpectedConditions.visibilityOfAllElements(webElements);
	}
	  /**
	   * An expectation for checking the element to be invisible
	   *
	   * @param element used to check its invisibility
	   * @return Boolean true when elements is not visible anymore
	   */
	protected static ExpectedCondition<Boolean> checkInvisibilityOf(WebElement webElement) {

		return ExpectedConditions.invisibilityOf(webElement);
	}
	
	  /**
	   * Constructor. A check is made that the given element is, indeed, a SELECT tag. If it is not,
	   * then an UnexpectedTagNameException is thrown.
	   *
	   * @param element SELECT element to wrap
	   * @throws UnexpectedTagNameException when element is not a SELECT
	   */
	protected Select buildSelect(WebElement webElement) {
		try {
			return new Select(webElement);
		} catch (UnexpectedTagNameException e) {
			log.error(e.getMessage());
			throw e;
		}
		
	}

}
