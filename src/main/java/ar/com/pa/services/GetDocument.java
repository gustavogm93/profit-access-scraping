package ar.com.pa.services;


import ar.com.pa.enums.utils.Summaries;
import ar.com.pa.model.CompanyOperation;
import ar.com.pa.model.financialsummary.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;



@Component
public class GetDocument {

    
    @Autowired
    ScrappingFetch ScrappingFetch;
    
    private static Logger logger = LoggerFactory.getLogger(GetDocument.class);
    

    
	public void getHtmlDocument(String url) throws Exception {

		try {

			CompanyOperation companyConstant = new CompanyOperation("bank-of-america");

			String companyCode = ScrappingFetch.generateCompanyCodeUrl(companyConstant.getTitle());

			Summaries[] summaries = Summaries.values();

			String summaryUrl = ScrappingFetch.generateSummaryUrl(companyCode, summaries[0]);

			Elements periodElements = ScrappingFetch.getElementsByTag(summaryUrl, "Th");

			Company company = new Company();

			ScrappingFetch.run(periodElements, company, summaries[0]);

		} catch (Exception e) {
			logger.error("Error log message");
			throw new Exception(e.getMessage());
		}
	}

}
