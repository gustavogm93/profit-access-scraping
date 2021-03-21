package ar.com.pa.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChromeDriverD {
	
	public static WebDriver driver;
	
	@Value("${chrome.driver}")
	private String chromeDriverPath;

	public void setUp() {

		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		this.driver = new ChromeDriver();

	}
	//TODO estructurar CrhomeDriverD y los servicios de fetch
	public void close() {

		this.driver.close();

	}
	
	public void getInv() {
		
		setUp();
		this.driver.get("https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-chrome-driver");
	}
	
	
	
	
	
	
	
}
