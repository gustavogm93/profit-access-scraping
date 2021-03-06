package ar.com.pa.services;


import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
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
import ar.com.pa.enums.utils.ScrappingConstant;
import ar.com.pa.enums.utils.Summaries;
import ar.com.pa.model.CompanyOperation;
import ar.com.pa.model.Instrument;
import ar.com.pa.model.financialsummary.Company;
import ar.com.pa.model.financialsummary.Summary;
import ar.com.pa.repository.CompanyRepository;
import ar.com.pa.repository.CompanyRepositoryFilter;
import ar.com.pa.utils.MapperUtils;
import ar.com.pa.utils.PatternResource;
import ar.com.pa.utils.ValidateUtils;

@Component
public class ScrappingImplement implements Scrapping{

	private final Logger logger = LoggerFactory.getLogger(ScrappingImplement.class);
	
	
	@Autowired
	public ValidateUtils validateUtils;
	
    public CompanyRepositoryFilter companyRepository;
    


	 
    public static List<Date> quarterPeriodList;
    public static FinancialSummary financialSummary;
    public static int dateIndex = 0;

    //FinancialSummaryYear lo reciba
    @Autowired
	public ScrappingImplement(CompanyRepositoryFilter companyRepository, T financialSummaryYear,Optional<List<Instrument>> instrumentList) {
    	
		this.companyRepository = companyRepository;
		this.summary = financialSummaryYear;

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

		Company company = new Company();
		
		getSummaryByPeriod(elements);
		

		company.setSummaryQuarter(summaryQuarter);

		
		xd(instrumentList);
		System.out.println("----");


		companyRepository.save(company);
		

	}
	
	@Override
	public String getCompanyCode(CompanyOperation companyOperation, Summaries summaryCode) {

		Document doc;
		try {
			doc = Jsoup.connect(generateCompanyCodeUrl(companyOperation.getTitle())).get();
			
			Element element = doc.select("div[data-pair-id]").first();

			String companyCode = element.attr("data-pair-id");
		
			 return companyCode;
		} catch (IOException e) {
			
			e.printStackTrace();
			return null;
			
		}
	}
	
	@Override
	public String generateCompanyCodeUrl(String companyTitle) {
		
		StringBuilder companyCode = new StringBuilder();
		
		companyCode.append(ScrappingConstant.codeUrl);
		
		return companyCode.toString();
		
	}
	
	@Override
	public String generateSummaryUrl(String codeCompany, Summaries summaryCode) {

		StringBuilder summaryUrl = new StringBuilder();
		
		summaryUrl.append(ScrappingConstant.fixUrl); 
		
		if(summaryCode == Summaries.FS) 
			 summaryUrl.append(ScrappingConstant.urlFs.replace("#", codeCompany));
		else
			summaryUrl.append(ScrappingConstant.urlNotFs.replace("#", codeCompany).replace("$", summaryCode.toString()));
			 
		return summaryUrl.toString();
	}
	
	@Override
	public Elements getElementsByTag(String urlSummary, String tag) {

		Document doc;
		
			try {
				doc = Jsoup.connect(urlSummary).get();	
				Elements summaryElements = doc.getElementsByTag(tag);
				
				return summaryElements;
			} catch (Exception e) {
				return null;
			}
		
	}
	
	
	public void getSummaryByPeriod(Elements e,FinancialSummary financialSummary,List<Instrument> instrumentList, Summary summaryPerYear) {

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

		summaryPerYear.setSummary(instrumentList);

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
	  

	  
	public List<Date> getQuarterPeriodDate(Elements p) {

		return p.stream().map(Element::ownText).distinct().filter(PatternResource::dateStringPattern)
				.map(MapperUtils::toDate).collect(Collectors.toList());

	}

	@Override
	public String getUrl(Document document, Summaries period) {
		// TODO Auto-generated method stub
		return null;
	}




}
