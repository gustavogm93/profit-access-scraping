package ar.com.pa.controller;

import ar.com.pa.services.ExtractByJsoupImpl;
import ar.com.pa.services.ExtractBySeleniumImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/scrapping")
@Tag(name = "Scrapping API")
@Data
public class ScrapingController {

	private ExtractByJsoupImpl jsonExtract;

	private ExtractBySeleniumImpl seleniumExtract;

	@Autowired
	public ScrapingController(ExtractBySeleniumImpl scrapingResourcesImpl, ExtractByJsoupImpl jsonExtract) {
		this.seleniumExtract = scrapingResourcesImpl;
		this.jsonExtract = jsonExtract;

	}

	@GetMapping("/region")
	public void getRegion() throws Exception {
		jsonExtract.fetchRegion();
	}

	@GetMapping("/marketIndex")
	public void getMarketValuesByRegion() throws Exception {
		seleniumExtract.executor();
	}

	
	
	
}
