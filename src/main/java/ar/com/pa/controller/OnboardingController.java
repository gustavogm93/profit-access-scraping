package ar.com.pa.controller;

import ar.com.pa.model.queue.CompanyOperationMessage;
import ar.com.pa.services.CompanyService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onboarding/stats")
@Tag(name = "OnboardingState API")
@Slf4j
@Data
public class OnboardingController{	

    private CompanyService companyService;
    
    @Autowired
    public OnboardingController(CompanyService companyService) {
		this.companyService = companyService;
		
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
    public void findGlobalLeads() throws Exception{ 
		/*
		CompanyOperationMessage companyOperationMessage = new CompanyOperationMessage();
		companyOperationMessage.setTitle("chimimport-ad-company");
		companyOperationMessage.setScrappingCode("41535");
		companyService.saveFromScratch(companyOperationMessage);
		System.out.println("---------------------------------------------------------------------");
		//doke.getHtmlDocument("https://www.investing.com/equities/coca-cola-co-financial-summary");
		
		*/
	}

	
	
	

	
}
