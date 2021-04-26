package ar.com.pa.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.concurrent.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.com.pa.collections.region.RegionService;
import ar.com.pa.collections.summary.ScrapingRegionService;
import ar.com.pa.scraping.InvestingFetchRegion;
import ar.com.pa.scraping.selenium.InvestingFetchCountry;
import lombok.Data;


@RestController
@RequestMapping("/scrapping")
@Tag(name = "Scrapping API")
@Data
public class ScrapingController {

	private InvestingFetchRegion jsonExtract;
	
	private ScrapingRegionService rs;
	
	private InvestingFetchCountry seleniumExtract;
	
	private final ExecutorService executorService;
	
	@Autowired
	public ScrapingController(InvestingFetchCountry scrapingResourcesImpl, InvestingFetchRegion jsonExtract, ScrapingRegionService rs, ExecutorService executorService) {
		this.seleniumExtract = scrapingResourcesImpl;
		this.jsonExtract = jsonExtract;
		this.executorService = executorService;
		this.rs = rs;
	}

	@GetMapping("/region")
	public void getRegion() throws Exception {
		jsonExtract.executor();
		
	}

	@GetMapping("/get")
	public void fetchStudent(@RequestParam(name = "region") String region) {
		seleniumExtract.executor(region);
	}
	

	
}
