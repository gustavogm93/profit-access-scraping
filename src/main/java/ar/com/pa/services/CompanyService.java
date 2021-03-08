package ar.com.pa.services;


import ar.com.pa.enums.utils.SummaryType;
import ar.com.pa.model.FetchOperation;
import ar.com.pa.model.StarterMessage;
import ar.com.pa.model.financialsummary.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class CompanyService {
	
    @Autowired
    ScrapingFetchImpl ScrapingFetch;
    
    private SummaryType[] summaries;
    
    
    
    private static Logger logger = LoggerFactory.getLogger(CompanyService.class);     
    
	public void saveFromScratch(FetchOperation fetchOperation) throws Exception {

		try {		

			Company company = new Company();
			company.setFetchOperation(fetchOperation);

			HashMap<SummaryType, String> urlList = new HashMap<SummaryType, String>();
			List<Date> summaryPeriods = new ArrayList<>();
			
			for (int i = 0; i < summaries.length; i++) {

				Entry<SummaryType, String> url = ScrapingFetch.buildSummaryUrl(company.getFetchOperation().getCode(), summaries[i]);
				urlList.put(url.getKey(), url.getValue());

				if (i == 0) {
					Elements periodElements = ScrapingFetch.getElementsByTag(url.getValue(), "Th");
					summaryPeriods.addAll(ScrapingFetch.getPeriods(periodElements));
				}

				ScrapingFetch.run(summaryPeriods, company, summaries[i], url.getValue());
			}
			fetchOperation.setUrlList(urlList);
			company.getFetchOperation().setUrlList(urlList);

		} catch (Exception e) {
			logger.error("Error log message");
			throw new Exception(e.getMessage());
		}
	}
	
	
	public void create(StarterMessage starterMessage) {
		
		FetchOperation fetchOperation = new FetchOperation();
		
		fetchOperation.setTitle(starterMessage.getCompanyTitle());
		
		fetchOperation.setCode((ScrapingFetch.getScrapingCodeByCompanyTitle(fetchOperation.getTitle())));
			
		fetchOperation.setState("STARTER");
		sender(fetchOperation);
	}
	
	public void sender(FetchOperation companyOperation) {
			
	
		}

}
