package ar.com.pa.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ar.com.pa.enums.financialsummary.FinancialSummary;
import ar.com.pa.utils.PatternResource;
import ar.com.pa.utils.ValidateUtils;
import ar.com.pa.enums.Periods;
import ar.com.pa.enums.UrlPattern;

@Service
class ScrappingWeb {

	private final Logger logger = LoggerFactory.getLogger(ScrappingWeb.class);

	public String summary;
	public List<String> valuePerSummaryList = new ArrayList<String>();
	public HashMap<String, String> financialSummaryMap = new HashMap<String, String>();
	
	public HashMap<String, String> getFinancialSummary(Elements elements, Elements periods) {	
		
		logger.info("Scrapping financial Summary into HashMap and return it");
		
		List<String> financialSummaryElements = elements.stream().map(Element::ownText)
											.filter(this::isSummaryString).collect(Collectors.toList());
		
		
		for(String element: financialSummaryElements) {
			fillFinancialSummary(element);
		}

		
		return this.financialSummaryMap;
		
	}
	
	public String getUrl(Document document, UrlPattern period) {
		
		Element element = document.select("div[data-pair-id]").first();
		
		String companyCode = element.attr("data-pair-id");
		
		return PatternResource.getFinancialSummaryUrl(companyCode, period);
	}
	
	public void fillFinancialSummary(String element) {

		if (ValidateUtils.isNumeric(element)) {
			this.valuePerSummaryList.add(this.valuePerSummaryList.size() + ":" + element);
		} else {
			this.summary = element;
		}
		
		if (this.valuePerSummaryList.size() == 4) {
			this.financialSummaryMap.put(this.summary, valuePerSummaryList.toString());
			this.valuePerSummaryList.clear();
		}
		
		}


	public String getSummaryByList(List<String> list) {
		return list.stream().filter(FinancialSummary::isSummary).findFirst().orElseThrow(
		        () -> new IllegalArgumentException("Not found value"));		
	}
	public String a (String s) {
		return s;
	}
	
	public boolean isSummaryString(String s) {
		
	return FinancialSummary.isSummary(s) || ValidateUtils.isNumeric(s);
	}
	
	
	
	
	

}
