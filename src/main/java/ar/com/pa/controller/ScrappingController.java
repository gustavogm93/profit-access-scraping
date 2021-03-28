package ar.com.pa.controller;


import ar.com.pa.services.ExtractByJsoupImpl;
import ar.com.pa.services.ExtractBySeleniumImpl;
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

	 private ExtractByJsoupImpl jsonExtract;

    private ExtractBySeleniumImpl seleniumExtract;
    
    @Autowired
    public ScrappingController(ExtractBySeleniumImpl scrapingResourcesImpl, ExtractByJsoupImpl jsonExtract) {
		this.seleniumExtract = scrapingResourcesImpl;
		this.jsonExtract = jsonExtract;
		
	}
    

    @GetMapping("/save")
    public void getCountryEnum() throws Exception{ 
		
		jsonExtract.fetchRegion();
		//scrapingConstantImpl.saveMarketIndex();
	}


    @GetMapping("/geter")
    public void getCountryEnume() throws Exception{ 
    	
    	seleniumExtract.testingGet();
		//scrapingConstantImpl.saveMarketIndex();
	}
	

	
}
