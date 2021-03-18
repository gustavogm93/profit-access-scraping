package ar.com.pa.controller;


import ar.com.pa.services.ScrapingConstantImpl;
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

    private ScrapingConstantImpl scrapingConstantImpl;
    
    @Autowired
    public ScrappingController(ScrapingConstantImpl scrapingConstantImpl) {
		this.scrapingConstantImpl = scrapingConstantImpl;
		
	}
    
	@io.swagger.v3.oas.annotations.Operation(summary = "Get onboarding state", description = "Get number of people with leads on onboarding state")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})	
    @GetMapping
    public void getCountryEnum() throws Exception{ 

		scrapingConstantImpl.getCountryConstant();
	}

	
	
	

	
}
