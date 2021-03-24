package ar.com.pa.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SeleniumDriver {

	@Value("${chrome.driver}")
	private String chromeDriverPath;

	public WebDriver get() {

		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		WebDriver driver = new ChromeDriver();
		return driver;

	}

	public void wait(WebDriver chromeDriver, String str) {

		WebDriverWait wait = new WebDriverWait(chromeDriver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(str)));
	}
}
