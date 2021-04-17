package ar.com.pa.controller;

import ar.com.pa.scraping.ExtractBySeleniumImpl;
import ar.com.pa.scraping.ScrapingRegionConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/scrapping")
@Tag(name = "Scrapping API")
@Data
public class ScrapingController {

	private ScrapingRegionConstant jsonExtract;
	
	private ExtractBySeleniumImpl seleniumExtract;
	
	
	private final ExecutorService executorService;
	@Autowired
	public ScrapingController(ExtractBySeleniumImpl scrapingResourcesImpl, ScrapingRegionConstant jsonExtract, ExecutorService executorService) {
		this.seleniumExtract = scrapingResourcesImpl;
		this.jsonExtract = jsonExtract;
		this.executorService = executorService;
	}

	@GetMapping("/region")
	public void getRegion() throws Exception {
		jsonExtract.executor();
	}

	@GetMapping("/get")
	public void fetchStudent(@RequestParam(name = "region") String region)   {
		seleniumExtract.executor(region);
	}
	
	
	

	
	
	
}
