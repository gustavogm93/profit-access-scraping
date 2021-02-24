package ar.com.pa.services;


import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.xml.bind.v2.util.QNameMap.Entry;

import ar.com.pa.enums.UrlPattern;
import ar.com.pa.repository.FinancialSummaryRepository;
import ar.com.pa.utils.MapperElement;
import ar.com.pa.utils.PatternResource;
import ar.com.pa.utils.ValidateUtils;

@Service
public class GetDocument {
	
	@Autowired
	ScrappingWeb scrappingWeb;
	
    @Autowired
    private FinancialSummaryRepository financialSummaryRepository;
    
    @Autowired
    ValidateUtils validateUtils;
    
	public void getHtmlDocument(String url) {
	    Document doc = null;
	    
		try {
			
			MapperElement.AAconvert("JUNIO 24");
			
			/*
			doc = Jsoup.connect(url).get();

			String urlComplete = scrappingWeb.getUrl(doc, UrlPattern.QuarterPeriod);
			
			doc = Jsoup.connect(urlComplete).get();
			    
		    Elements tdTag = doc.getElementsByTag("td");
		    Elements thTag = doc.getElementsByTag("th");
		    HashMap<String, String> listHash = scrappingWeb.getFinancialSummary(tdTag, thTag);
		  
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
	// TODO: handle exception
}
	}
	
	

}
