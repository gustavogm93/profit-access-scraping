package ar.com.pa.services;


import ar.com.pa.model.CompanyOperationMessage;
import ar.com.pa.model.FetchOperation;
import ar.com.pa.model.Industry;
import ar.com.pa.model.Instrument;
import ar.com.pa.model.Profile;
import ar.com.pa.model.StarterMessage;
import ar.com.pa.model.financialsummary.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;


@Service
public class CompanyService {
	
    
    public ScrapingFetchImpl ScrapingFetch;
    
    
    @Autowired
    public CompanyService(ScrapingFetchImpl scrapingFetch) {
		ScrapingFetch = scrapingFetch;
	}

	private static Logger logger = LoggerFactory.getLogger(CompanyService.class);     
    
	public void saveFromScratch(CompanyOperationMessage companyOperationMessage) throws Exception {

		try {		
			Company company = new Company();
			Industry profile = new Industry();
			
			FetchOperation fetchOperation = new FetchOperation();

			if (Objects.nonNull(companyOperationMessage.getIdCompany())) {}
			 /// BUSCAR EN LA BASE DE DATOS E ASIGNARLES EL VALOR A COMPANY Y FETCH
			else {
				fetchOperation.setTitle(companyOperationMessage.getTitle());
				fetchOperation.setCode(companyOperationMessage.getScrappingCode());
				company.setFetchOperation(fetchOperation);
			}			
			
				ScrapingFetch.getCompanyProfileByCompanyTitle(fetchOperation.getTitle(), profile);

				List<LocalDate> summaryPeriodTime = new ArrayList<>();

				String url = ScrapingFetch.buildSummaryUrl(company.getFetchOperation().getCode());
				
				Document doc = Jsoup.connect(url).get();
				
					Elements periodElements = ScrapingFetch.getElementsByTag("Th", doc);
					summaryPeriodTime.addAll(ScrapingFetch.getPeriods(periodElements));
				

				List<Instrument> instrumentsPerSummary = new ArrayList<>();
				
				Elements scrapingElements = ScrapingFetch.getElementsByTag("Td", doc);
				
				List<Instrument> listInstrument = ScrapingFetch.getSummaryByPeriod(scrapingElements, instrumentsPerSummary, summaryPeriodTime);
				Gson gson = new Gson();
				
				String jsonInString = gson.toJson(listInstrument);
				
				ScrapingFetch.xd(listInstrument);
				System.out.println(jsonInString);
				System.out.println("----");


		} catch (Exception e) {
			logger.error("Error log message");
			throw new Exception(e.getMessage());
		}
	}
	
	
	
	public void create(StarterMessage starterMessage) {
		
		FetchOperation fetchOperation = new FetchOperation();
		
		fetchOperation.setTitle(starterMessage.getCompanyTitle());
		
		fetchOperation.setCode((ScrapingFetch.getScrapingCodeByCompanyTitle(fetchOperation.getTitle())));
			
		//fetchOperation.setState("STARTER");
		sender(fetchOperation);
	}
	
	public void sender(FetchOperation companyOperation) {
			
	
		}


	
	
}
