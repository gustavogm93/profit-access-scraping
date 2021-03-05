package ar.com.pa.services;


import java.lang.reflect.Type;
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;

import ar.com.pa.enums.financialsummary.FinancialSummary;
import ar.com.pa.enums.utils.UrlPattern;
import ar.com.pa.model.Instrument;
import ar.com.pa.model.financialsummary.Company;
import ar.com.pa.model.financialsummary.Summary;
import ar.com.pa.repository.CompanyRepository;
import ar.com.pa.utils.MapperUtils;
import ar.com.pa.utils.PatternResource;
import ar.com.pa.utils.ValidateUtils;

@Component
public class ScrappingImplement <T extends Summary> implements Scrapping<T>{

	private final Logger logger = LoggerFactory.getLogger(ScrappingImplement.class);
	
	
	@Autowired
	public ValidateUtils validateUtils;
	
    public CompanyRepository companyRepository;
    
    public T summaryYear;
    public T summaryQuarter;
	
    public List<Instrument> instrumentList;

	 
    public static List<Date> quarterPeriodList;
    public static FinancialSummary financialSummary;
    public static int dateIndex = 0;

    //FinancialSummaryYear lo reciba
    @Autowired
	public ScrappingImplement(CompanyRepository companyRepository, T financialSummaryYear,
			T financialSummaryQuarter, Optional<List<Instrument>> instrumentList) {
		this.companyRepository = companyRepository;
		summaryYear = financialSummaryYear;
		summaryQuarter = financialSummaryQuarter;
		this.instrumentList = new ArrayList<>();
	}
    
/*
	public ScrappingImplement(CompanyRepository companyRepository, T financialSummaryYear,
			, Optional<List<Instrument>> instrumentList) {
		this.companyRepository = companyRepository;
		summaryYear = financialSummaryYear;
		summaryQuarter = financialSummaryQuarter;
		this.instrumentList = new ArrayList<>();
	}*/
   
	public ScrappingImplement() {}


	public void saveSummary(Elements elements, Elements periods ) {

		logger.info("Scrapping financial Summary into HashMap and return it");
		
		quarterPeriodList = getQuarterPeriodDate(periods);

		Company<T> company = new Company<T>();
		
		getSummaryByPeriod(elements);
		

		company.setSummaryQuarter(summaryQuarter);

		
		xd(instrumentList);
		System.out.println("----");


		companyRepository.save(company);
		

	}

	public String getUrl(Document document, UrlPattern period) {

		Element element = document.select("div[data-pair-id]").first();

		String companyCode = element.attr("data-pair-id");

		return PatternResource.getFinancialSummaryUrl(companyCode, period);
	}

	public void getSummaryByPeriod(Elements e) {

		e.stream()
		.map(Element::ownText)
		.filter(validateUtils::isSummaryModelValue)
		.forEach((element) -> {					
					if (validateUtils.isSummaryObject(element)) {
						financialSummary = FinancialSummary.getFinancialSummaryByString(element);
						dateIndex = 0;
					} else 
						instrumentList.add(fillInstrument(element));
				});

		summaryQuarter.setSummary(instrumentList);

	}

	@Override
	public Instrument fillInstrument(String instrumentValue)
	{
		
		Instrument instrumentToAdd = new Instrument();
		instrumentToAdd.setTitle(financialSummary.getTitle());
		instrumentToAdd.setValue(MapperUtils.stringToNum(instrumentValue));
		instrumentToAdd.setPeriodEnding(quarterPeriodList.get(dateIndex));
		
		dateIndex++;
		
		return instrumentToAdd;
	}
	
	
	
	
	  public static void xd(List<Instrument> instru) {
		 for(Instrument A:instru) {
			 System.out.println(A.toString());
		 }
	    }
	  
	  
	  @SuppressWarnings("serial")
	  private final TypeToken<T> typeToken = new TypeToken<T>(getClass()) {};
	  private final Type type = typeToken.getType();
	  public Type getType() {return type;}
	  
	public List<Date> getQuarterPeriodDate(Elements p) {

		return p.stream().map(Element::ownText).distinct().filter(PatternResource::dateStringPattern)
				.map(MapperUtils::toDate).collect(Collectors.toList());

	}




}
