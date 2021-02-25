package ar.com.pa.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
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
import ar.com.pa.enums.utils.UrlPattern;
import ar.com.pa.model.financialsummary.FinancialSummaryDTO;
import ar.com.pa.utils.MapperUtils;
import ar.com.pa.utils.PatternResource;
import ar.com.pa.utils.ValidateUtils;


public class ScrappingFormat {

	private final Logger logger = LoggerFactory.getLogger(ScrappingFormat.class);

	private FinancialSummary summary;
	private HashMap<String, String> financialSummaryMap = new HashMap<>();
	private List<Date> quarterPeriodList = new ArrayList<>();
	private List <FinancialSummaryDTO> financialSummaryList = new ArrayList<>();
	private int companyId;
	private int index = 0;
	public ScrappingFormat(int companyId) {
		this.companyId = companyId;
	}
	
	public HashMap<String, String> getFinancialSummary(Elements elements, Elements periods) {	
		logger.info("Scrapping financial Summary into HashMap and return it");

		quarterPeriodList = periods.stream().map(Element::ownText)
				.filter(PatternResource::dateStringPattern)
				.map(MapperUtils::toDate)
				.distinct()
				.collect(Collectors.toList());
		
		
		List<String> lista= elements.stream().map(Element::ownText)
						.filter(ValidateUtils::isSummaryValue).collect(Collectors.toList());
		
		
		elements.stream().map(Element::ownText)
						.filter(ValidateUtils::isSummaryValue)
				        .forEach(this::fillFinancialSummary);

		
		System.out.println(financialSummaryList.toString());
		
		return this.financialSummaryMap;
		
	}
	
	public String getUrl(Document document, UrlPattern period) {
		
		Element element = document.select("div[data-pair-id]").first();
		
		String companyCode = element.attr("data-pair-id");
		
		return PatternResource.getFinancialSummaryUrl(companyCode, period);
	}
	
	public void fillFinancialSummary(String element) {

		if (ValidateUtils.isNumOrEmpty(element)) {

			FinancialSummaryDTO financialSummaryDTO = new FinancialSummaryDTO(this.summary, quarterPeriodList.get(this.index),
																			  MapperUtils.toCompanyValue(element),this.companyId);
			
			financialSummaryList.add(financialSummaryDTO);
			this.index++;
		} else {
			this.summary = FinancialSummary.getFinancialSummaryByString(element);
			this.index = 0;
		}
		
		}


	public int getId() {return companyId;}
	public void setcompanyId(int id) {this.companyId = id;}
	
	

}
