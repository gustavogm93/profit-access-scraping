package ar.com.pa.controller;

import ar.com.pa.services.ScrapingConstantImpl;
import ar.com.pa.utils.ChromeDriverD;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scrapping")
@Tag(name = "Scrapping API")
@Data
public class ScrappingController{	

	
	 private ChromeDriverD a;
    private ScrapingConstantImpl scrapingConstantImpl;
    
    @Autowired
    public ScrappingController(ScrapingConstantImpl scrapingConstantImpl, ChromeDriverD a) {
		this.scrapingConstantImpl = scrapingConstantImpl;
		this.a = a;
		
	}
    
	@io.swagger.v3.oas.annotations.Operation(summary = "", description = "")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})	
    @GetMapping
    public void getCountryEnum() throws Exception{ 
		
		a.getInv();
		//scrapingConstantImpl.saveMarketIndex();
	}

	
	
	

	
}
