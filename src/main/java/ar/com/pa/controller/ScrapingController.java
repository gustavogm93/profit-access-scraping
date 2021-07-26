package ar.com.pa.controller;

import ar.com.pa.scraping.ScrapingCountryStrategy;
import ar.com.pa.scraping.ScrapingCoverageStrategy;
import ar.com.pa.scraping.ScrapingRegionStrategy;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/scraping")
@Tag(name = "Scraping API")
@Data
public class ScrapingController {

	@Value("${chrome.driver.path}")
	private String ChromeDriverPathResource;

	private ScrapingRegionStrategy scrapingRegion;

	/*private ScrapingCountryStrategy scrapingCountry;
	
	 private ScrapingCoverageStrategy scrapingCoverageCountry;
	
	@Autowired
	public ScrapingController(ScrapingCountryStrategy scrapingCountry, ScrapingRegionStrategy scrapingRegion, ScrapingCoverageStrategy scrapingCoverageCountry ) {
		this.scrapingCountry = scrapingCountry;
		this.scrapingRegion = scrapingRegion;
		this.scrapingCoverageCountry = scrapingCoverageCountry;
	}*/

	@Autowired
	public ScrapingController(ScrapingRegionStrategy scrapingRegion ) {

		this.scrapingRegion = scrapingRegion;
	}


	@GetMapping("/regions")
	public void getRegionExtractedData() throws Exception {
		scrapingRegion.executor();
	}
/*
	@GetMapping("/countries")
	public void getCountryExtractedData(@RequestParam(name = "region") String region) {
		scrapingCountry.executor(region);
	}*/

	/*@GetMapping("coverage/country")
	public void getCoverageCountryExtractedData(@RequestParam(name = "region") String region) {
		scrapingCoverageCountry.executor(region);
	}*/

	
	
	
	
	
	
	
	
	
	
	
	
}
