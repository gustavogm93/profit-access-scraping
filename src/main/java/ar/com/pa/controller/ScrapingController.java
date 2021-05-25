package ar.com.pa.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.collection.HashSet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import com.google.common.collect.Sets;

import ar.com.pa.collections.marketIndex.MarketIndexDTO;
import ar.com.pa.collections.marketIndex.MarketIndexProp;
import ar.com.pa.collections.share.ShareProp;
import ar.com.pa.generics.Property;
import ar.com.pa.scraping.ScrapingCountryStrategy;
import ar.com.pa.scraping.ScrapingCoverageStrategy;
import ar.com.pa.scraping.ScrapingRegionStrategy;
import lombok.Data;


@RestController
@RequestMapping("/scraping")
@Tag(name = "Scraping API")
@Data
public class ScrapingController {

	private ScrapingRegionStrategy scrapingRegion;
	
	private ScrapingCountryStrategy scrapingCountry;
	
	private ScrapingCoverageStrategy scrapingCoverageCountry;
	
	@Autowired
	public ScrapingController(ScrapingCountryStrategy scrapingCountry, ScrapingRegionStrategy scrapingRegion, ScrapingCoverageStrategy scrapingCoverageCountry ) {
		this.scrapingCountry = scrapingCountry;
		this.scrapingRegion = scrapingRegion;
		this.scrapingCoverageCountry = scrapingCoverageCountry;
	}

	@GetMapping("/region")
	public void getRegionExtractedData() throws Exception {
		scrapingRegion.executor();
	}

	@GetMapping("/country")
	public void getCountryExtractedData(@RequestParam(name = "region") String region) {
		scrapingCountry.executor(region);
	}
	

	@GetMapping("coverage/country")
	public void getCoverageCountryExtractedData(@RequestParam(name = "region") String region) {
		scrapingCoverageCountry.executor(region);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
