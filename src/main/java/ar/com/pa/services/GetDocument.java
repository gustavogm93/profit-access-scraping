package ar.com.pa.services;


import ar.com.pa.model.financialsummary.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ar.com.pa.enums.utils.UrlPattern;


@Component
public class GetDocument {

    
    @Autowired
    @Qualifier("Financial")
    ScrappingImplement<FinancialSummary> s;
    
    @Autowired
    @Qualifier("Balance")
    ScrappingImplement<BalanceSheet> sa;
    
    private static Logger logger = LoggerFactory.getLogger(GetDocument.class);
    

    
	public void getHtmlDocument(String url) throws Exception {
	    Document doc = null;
	    
		try {
	
			//FINANCIAL SUMMARY
			doc = Jsoup.connect(url).get();
				

			String urlComplete = s.getUrl(doc, UrlPattern.QuarterPeriod);
			
			doc = Jsoup.connect(urlComplete).get();
			    
		    Elements tdTag = doc.getElementsByTag("td");
		    Elements thTag = doc.getElementsByTag("th");
		    
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
