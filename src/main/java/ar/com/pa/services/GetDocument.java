package ar.com.pa.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.pa.enums.FinancialSummary;
import ar.com.pa.enums.UrlPattern;
import ar.com.pa.repository.FinancialSummaryRepository;
import ar.com.pa.utils.PatternResource;
import ar.com.pa.utils.ValidateUtils;

@Service
public class GetDocument {
	
	@Autowired
	ScrappingFormat scrappingFormat;
	
    @Autowired
    private FinancialSummaryRepository financialSummaryRepository;
    
    @Autowired
    ValidateUtils validateUtils;
    
	public Document getHtmlDocument(String url) {
	    Document doc = null;
	    /*
		try {
			
			doc = Jsoup.connect(url).get();

			String urlComplete = getUrl(doc, UrlPattern.QuarterPeriod);
			
			doc = Jsoup.connect(urlComplete).get();
			    
		    Elements spanTags = doc.getElementsByTag("td");
		    HashMap<String, String> listHash = scrappingFormat.reduceFinancialSummary(spanTags);
		    */
	    
	    HashMap<String, String> listMock = new HashMap<String, String>();
	    listMock.put("Total Revenue", "1:5470 2:4881 3:3533 4:4010");
	    listMock.put("Total Assets", "1:23214 2:56252 3:39993 4:44270");
	    ValidateUtils validate = new ValidateUtils();
	    
	    validateUtils.bacMapper(listMock);
	    
		return doc;
}
	
	
	
	public static String getUrl(Document document, UrlPattern period) {
		
		Element element = document.select("div[data-pair-id]").first();
		
		String companyCode = element.attr("data-pair-id");
		
		return PatternResource.getFinancialSummaryUrl(companyCode, period);
	}
}
