package ar.com.pa.controller;

import ar.com.pa.services.ExtractByJsoupImpl;
import ar.com.pa.services.ExtractBySeleniumImpl;
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

	private ExtractByJsoupImpl jsonExtract;
	
	private ExtractBySeleniumImpl seleniumExtract;
	
	private final ExecutorService executorService;
	@Autowired
	public ScrapingController(ExtractBySeleniumImpl scrapingResourcesImpl, ExtractByJsoupImpl jsonExtract, ExecutorService executorService) {
		this.seleniumExtract = scrapingResourcesImpl;
		this.jsonExtract = jsonExtract;
		this.executorService = executorService;
	}

	@GetMapping("/region")
	public void getRegion() throws Exception {
		jsonExtract.fetchRegion();
	}

	@GetMapping("/get")
	public void fetchStudent(@RequestParam(name = "region") String region)   {
		seleniumExtract.executor(region);
	}
	
	
	

	
	
	
}
