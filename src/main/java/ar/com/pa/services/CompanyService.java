package ar.com.pa.services;


import ar.com.pa.model.company.Flow;
import ar.com.pa.model.company.Industry;
import ar.com.pa.model.company.Instrument;
import ar.com.pa.model.company.CompanyDTO;

import ar.com.pa.model.queue.CompanyOperationMessage;
import ar.com.pa.model.queue.StarterMessage;

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
			CompanyDTO company = new CompanyDTO();
			Industry profile = new Industry();
			
			Flow flow = new Flow();

			if (Objects.nonNull(companyOperationMessage.getIdCompany())) {}
			 /// BUSCAR EN LA BASE DE DATOS E ASIGNARLES EL VALOR A COMPANY Y FETCH
			else {
				flow.setTitle(companyOperationMessage.getTitle());
				flow.setCode(companyOperationMessage.getScrappingCode());
				company.setFetchOperation(flow);
			}			
			
				ScrapingFetch.getCompanyProfileByCompanyTitle(flow.getTitle(), profile);

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
		
		Flow fetchOperation = new Flow();
		
		fetchOperation.setTitle(starterMessage.getCompanyTitle());
		
		fetchOperation.setCode((ScrapingFetch.getScrapingCodeByCompanyTitle(fetchOperation.getTitle())));
			
		//fetchOperation.setState("STARTER");
		sender(fetchOperation);
	}
	
	public void sender(Flow companyOperation) {
			
	
		}


	
	
}
