package ar.com.pa.controller;

import ar.com.pa.repository.FinancialSummaryRepository;
import ar.com.pa.services.GetDocument;
import ar.com.pa.services.LeadsServices;
import ar.com.pa.services.RestService;
import ar.com.pa.utils.ValidateUtils;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onboarding/stats")
@Tag(name = "OnboardingState API")
@Slf4j
public class OnboardingController {	

    @Autowired 
    private LeadsServices leadser;
    
    @Autowired
    private RestService rest;
    
    @Autowired
    private GetDocument doke;
    
    @Autowired
    private ValidateUtils validate;
    
    @Autowired
    private FinancialSummaryRepository financialSummaryRepository;
    
	@io.swagger.v3.oas.annotations.Operation(summary = "Get onboarding state", description = "Get number of people with leads on onboarding state")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})	
    @GetMapping
    public void findGlobalLeads(){ 
		
		doke.getHtmlDocument("https://www.investing.com/equities/bank-of-america-financial-summary");
		System.out.println("---------------------------------------------------------------------");
		//doke.getHtmlDocument("https://www.investing.com/equities/coca-cola-co-financial-summary");
	}


	
}
