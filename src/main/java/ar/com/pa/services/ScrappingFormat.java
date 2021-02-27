package ar.com.pa.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ar.com.pa.enums.financialsummary.FinancialSummary;
import ar.com.pa.enums.utils.UrlPattern;
import ar.com.pa.model.financialsummary.FinancialSummaryDTO;
import ar.com.pa.utils.MapperUtils;
import ar.com.pa.utils.PatternResource;
import ar.com.pa.utils.ValidateUtils;


public class ScrappingFormat {

	private final Logger logger = LoggerFactory.getLogger(ScrappingFormat.class);

	private FinancialSummary summary;
	private List<Date> quarterPeriodList = new ArrayList<>();
	private int index = 0;
	public ScrappingFormat() {}
	
	public void getFinancialSummary(Elements elements, Elements periods) {	
		
		logger.info("Scrapping financial Summary into HashMap and return it");
	
		this.quarterPeriodList = getQuarterPeriodDate(periods);
		
		List<String> validElements = getValidElements(elements);

		List <FinancialSummaryDTO> financialSummaryList = getFinancialSummaryList(validElements);
		
	
	}
	
	public String getUrl(Document document, UrlPattern period) {
		
		Element element = document.select("div[data-pair-id]").first();
		
		String companyCode = element.attr("data-pair-id");
		
		return PatternResource.getFinancialSummaryUrl(companyCode, period);
	}
	
	
	public List<String> getValidElements(Elements e) {

		List<String> validElements = e.stream().map(Element::ownText)
											   .filter(ValidateUtils::isSummaryModelValue)
											   .collect(Collectors.toList());

		
		return validElements;
	}
	public List<Date> getQuarterPeriodDate(Elements p) {
		
	  return p.stream().map(Element::ownText)
					   .distinct()
					   .filter(PatternResource::dateStringPattern)
					   .map(MapperUtils::toDate)
					   .collect(Collectors.toList());
		
		
	}
	
	public List <FinancialSummaryDTO> getFinancialSummaryList(List<String> validElements){
		
	 return validElements.stream()
						 .map(this::getFinancialSummary)
						 .filter(Objects::nonNull)
						 .collect(Collectors.toList());
								 	 
								 	 
	}
	
	public FinancialSummaryDTO getFinancialSummary(String element) {
	
		if (ValidateUtils.isNumOrEmpty(element)) {

			FinancialSummaryDTO financialSummaryDTO = new FinancialSummaryDTO(this.summary, quarterPeriodList.get(this.index),
																			  MapperUtils.toCompanyValue(element));

			this.index++;
			return financialSummaryDTO;
		} else {
			this.summary = FinancialSummary.getFinancialSummaryByString(element);
			this.index = 0;
			return null;
		}
		
		}

}
