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
    ScrappingImplement scrappingImplement;
    
    private static Logger logger = LoggerFactory.getLogger(GetDocument.class);
    

    
	public void getHtmlDocument(String url) throws Exception {

	    
		try {

			CompanyOperation companyConstant = new CompanyOperation("bank-of-america");	
			
			String companyCode = scrappingImplement.generateCompanyCodeUrl(companyConstant.getTitle());
			
			Summaries[] summaries = Summaries.values();
			
			String summaryUrl = scrappingImplement.generateSummaryUrl(companyCode, summaries[0]);
			
			Elements valueElements = scrappingImplement.getElementsByTag(summaryUrl, "Td");
			Elements periodElements = scrappingImplement.getElementsByTag(summaryUrl, "Th");

			
		    
		    s.saveSummary(tdTag, thTag);
		   // sa.saveSummary(tdTag, thTag);
		   /*
		    //INCOME STATEMENT
		    
			doc = Jsoup.connect(url).get();
			
			String urlComplete2 = scrapping.getUrl(doc, UrlPattern.QuarterPeriod);
			
			doc = Jsoup.connect(urlComplete2).get();
		    */
		    
	    /*
	    HashMap<String, String> listMock = new HashMap<String, String>();
	    listMock.put("Total Revenue", "1:5470 2:4881 3:3533 4:4010");
	    listMock.put("Total Assets", "1:23214 2:56252 3:39993 4:44270");
	   
	    
	    for (Map.Entry<String, String> entry : listHash.entrySet()) {
	        String key = entry.getKey();
	        Object value = entry.getValue();
	        System.out.println("key: " +key);
	        System.out.println("value: " +value);
	        
	        System.out.println("--------------------");
	    }
	     */
	    //validateUtils.bacMapper(listMock);
	    
	
}catch (Exception e) {
	 logger.error("Error log message");
	throw new Exception(e.getMessage());
}
	}
	
	

}
