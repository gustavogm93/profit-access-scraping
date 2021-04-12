package ar.com.pa.scraping;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;

public interface SeleniumScraping {

	static WebDriver driver = null;
	static Wait<WebDriver> wait = null;
	public final long withoutWait = 6;
	public final long pollingEvery = 1200;

	public void executor(String code);

	public void initializeDriver();

}
