package ar.com.pa.services;


import java.sql.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.pa.enums.financialsummary.FinancialSummary;

import ar.com.pa.enums.utils.UrlPattern;
import ar.com.pa.model.InstrumentDTO;
import ar.com.pa.model.financialsummary.CompanyDTO;
import ar.com.pa.model.financialsummary.Summary;
import ar.com.pa.repository.CompanyRepository;
import ar.com.pa.utils.MapperUtils;
import ar.com.pa.utils.PatternResource;
import ar.com.pa.utils.ValidateUtils;

@Service
public class ScrappingImplement <T extends Summary> implements Scrapping{

	private final Logger logger = LoggerFactory.getLogger(ScrappingImplement.class);
	
	
    private CompanyRepository companyRepository;
    
    private T summaryYear;
    private T summaryQuarter;
	
	public List<InstrumentDTO> instrumentList;

	 
	public static List<Date> quarterPeriodList;
	 
	public static FinancialSummary financialSummary;
	public static int dateIndex = 0;


	public ScrappingImplement() {}
	
	@Autowired
	public ScrappingImplement(CompanyRepository companyRepository, T financialSummaryYear,
			T financialSummaryQuarter, Optional<List<InstrumentDTO>> instrumentList) {
		this.companyRepository = companyRepository;
		summaryYear = financialSummaryYear;
		summaryQuarter = financialSummaryQuarter;
		this.instrumentList = new ArrayList<>();
	}


	public void saveSummary(Elements elements, Elements periods) {

		logger.info("Scrapping financial Summary into HashMap and return it");

		CompanyDTO company = new CompanyDTO();

		quarterPeriodList = getQuarterPeriodDate(periods);

		getSummaryByPeriod(elements);
		
		company.setSummaryQuarter(summaryQuarter);		
		
		xd(instrumentList);
		System.out.println("----");

		
		/*
		companyRepository.save(company);
		
		*/
	}

	public String getUrl(Document document, UrlPattern period) {

		Element element = document.select("div[data-pair-id]").first();

		String companyCode = element.attr("data-pair-id");

		return PatternResource.getFinancialSummaryUrl(companyCode, period);
	}

	public void getSummaryByPeriod(Elements e) {

		e.stream()
		.map(Element::ownText)
		.filter(ValidateUtils::isSummaryModelValue)
		.forEach((element) -> {					
					if (ValidateUtils.isSummaryObject(element)) {
						financialSummary = FinancialSummary.getFinancialSummaryByString(element);
						dateIndex = 0;
					} else 
						instrumentList.add(fillInstrument(element));
				});
		
		
		summaryQuarter.setInstrumentList(instrumentList);

	}

	@Override
	public InstrumentDTO fillInstrument(String instrumentValue)
	{
		
		InstrumentDTO instrumentToAdd = new InstrumentDTO();
		instrumentToAdd.setTitle(financialSummary.getTitle());
		instrumentToAdd.setValue(MapperUtils.stringToNum(instrumentValue));
		instrumentToAdd.setPeriodEnding(quarterPeriodList.get(dateIndex));
		
		dateIndex++;
		
		return instrumentToAdd;
	}
	
	
	
	
	  public static void xd(List<InstrumentDTO> instru) {
		 for(InstrumentDTO A:instru) {
			 System.out.println(A.toString());
		 }
	    }
	  
	public List<Date> getQuarterPeriodDate(Elements p) {

		return p.stream().map(Element::ownText).distinct().filter(PatternResource::dateStringPattern)
				.map(MapperUtils::toDate).collect(Collectors.toList());

	}



}
