package ar.com.pa.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.concurrent.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.com.pa.collections.region.RegionService;
import ar.com.pa.scraping.ScrapingRegion;
import ar.com.pa.scraping.selenium.InvestmentFetchCountry;
import lombok.Data;


@RestController
@RequestMapping("/scrapping")
@Tag(name = "Scrapping API")
@Data
public class ScrapingController {

	private ScrapingRegion jsonExtract;
	
	private RegionService rs;
	
	private InvestmentFetchCountry seleniumExtract;
	
	
	private final ExecutorService executorService;
	@Autowired
	public ScrapingController(InvestmentFetchCountry scrapingResourcesImpl, ScrapingRegion jsonExtract, RegionService rs, ExecutorService executorService) {
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
