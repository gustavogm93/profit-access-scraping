package ar.com.pa.scraping;

import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public abstract class AbstractSeleniumScraping implements SeleniumScraping{

	/**
	 * Web Driver
	 */
	private WebDriver driver;
	/**
	 * Path to Chrome Driver in resources
	 */
	private URL chromeDriverPath;
	/**
	 * Wait for every Elements
	 */
	private Wait<WebDriver> wait;
	/**
	 * Timeout seconds for wait Elements
	 */
	private long timeoutSeconds;
	
	/**
	 * Polling every N (seconds)
	 */
	private long pollingEvery;

	
	
}
