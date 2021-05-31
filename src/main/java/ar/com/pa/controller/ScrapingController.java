package ar.com.pa.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import ar.com.pa.scraping.ScrapingRegionStrategy;
import lombok.Data;


@RestController
@RequestMapping("/scraping")
@Tag(name = "Scraping API")
@Data
public class ScrapingController {

	private ScrapingRegionStrategy scrapingRegion;
	/*
	private ScrapingCountryStrategy scrapingCountry;
	
	private ScrapingCoverageStrategy scrapingCoverageCountry;
	
	@Autowired
	public ScrapingController(ScrapingCountryStrategy scrapingCountry, ScrapingRegionStrategy scrapingRegion, ScrapingCoverageStrategy scrapingCoverageCountry ) {
		this.scrapingCountry = scrapingCountry;
		this.scrapingRegion = scrapingRegion;
		this.scrapingCoverageCountry = scrapingCoverageCountry;
	}
	*/
	@Autowired
	public ScrapingController(ScrapingRegionStrategy s) {
		this.scrapingRegion = s;
	}
	
	
	@GetMapping("/region")
	public void getRegionExtractedData() throws Exception {
		//scrapingRegion.executor();
		
		List<Integer> a = List.of(1,2,3);
		List<Integer> b = Collections.emptyList();
		
		for (Integer integer : a) {
			System.out.println(integer);
		}
		
		for (Integer integer : b) {
			System.out.println(integer);
		}
		System.out.println("ya paso");
		
	}




	
/*
	@GetMapping("/country")
	public void getCountryExtractedData(@RequestParam(name = "region") String region) {
		scrapingCountry.executor(region);
	}
	

	@GetMapping("coverage/country")
	public void getCoverageCountryExtractedData(@RequestParam(name = "region") String region) {
		scrapingCoverageCountry.executor(region);
	}
	*/
	
	
	
	
	
	
	
	
	
	
	
	
}
