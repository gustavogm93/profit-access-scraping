package ar.com.pa.services;


import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ar.com.pa.enums.financialsummary.FinancialSummary;
import ar.com.pa.enums.utils.ScrappingConstant;
import ar.com.pa.enums.utils.Summaries;
import ar.com.pa.model.CompanyOperation;
import ar.com.pa.model.Instrument;
import ar.com.pa.model.financialsummary.BalanceSheetDTO;
import ar.com.pa.model.financialsummary.CashFlowStatementDTO;
import ar.com.pa.model.financialsummary.Company;
import ar.com.pa.model.financialsummary.FinancialSummaryDTO;
import ar.com.pa.model.financialsummary.IncomeStatementDTO;

import ar.com.pa.repository.CompanyRepositoryFilter;
import ar.com.pa.utils.MapperUtils;
import ar.com.pa.utils.PatternResource;
import ar.com.pa.utils.ValidateUtils;

@Component
public class ScrappingFetchImpl implements ScrappingFetch{

	private final Logger logger = LoggerFactory.getLogger(ScrappingFetchImpl.class);

	public ValidateUtils validateUtils;

    public CompanyRepositoryFilter companyRepository;
    	
    public FinancialSummary financialSummary;

    public static int dateIndex = 0;

	@Autowired
	public ScrappingFetchImpl(ValidateUtils validateUtils, CompanyRepositoryFilter companyRepository,
		FinancialSummary financialSummary) {
	super();
	this.validateUtils = validateUtils;
	this.companyRepository = companyRepository;
	this.financialSummary = financialSummary;
}


	public void saveSummary(Elements periods, Company company, Summaries summarie ) {

		logger.info("Scrapping financial Summary into HashMap and return it");
		
		List<Date> intervalTimeList = getQuarterPeriodDate(periods);
		
		List<Instrument> instrumentList = new ArrayList<>();

		Elements elements = getElementsByTag(generateSummaryUrl(company.getCode(), summarie), "TD");
		
		getSummaryByPeriod(elements, instrumentList, intervalTimeList, summarie);
		
		saveSummaryInCompany(company, instrumentList, summarie);
		
		xd(instrumentList);
		System.out.println("----");


		//companyRepository.save(company);
		

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
	
	@Override
	public void getSummaryByPeriod(Elements e,List<Instrument> instrumentList, List<Date> intervalTimeList, Summaries summaryPerYear) {

		
		e.stream()
		.map(Element::ownText)
		.filter(validateUtils::isSummaryModelValue)
		.forEach((element) -> {		
			
					if (validateUtils.isSummaryObject(element)) {
						
						financialSummary = FinancialSummary.getFinancialSummaryByString(element);
						dateIndex = 0;
					} else {
						instrumentList.add(fillInstrument(element, intervalTimeList));
					}
				});

	}

	
	@Override
	public Instrument fillInstrument(String instrumentValue, List<Date> intervalTimeList)
	{
		
		Instrument instrumentToAdd = new Instrument();
		instrumentToAdd.setTitle(financialSummary.getTitle());
		instrumentToAdd.setValue(MapperUtils.stringToNum(instrumentValue));
		instrumentToAdd.setPeriodEnding(intervalTimeList.get(dateIndex));
		
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

	
	public void saveSummaryInCompany(Company company, List<Instrument> instrumentList,Summaries summarie) {
	
		switch(summarie) {
		
		case FS -> {
			FinancialSummaryDTO FinancialSummary = new FinancialSummaryDTO();
			FinancialSummary.setSummary(instrumentList);
			company.setFinancialSummary(FinancialSummary);
		}
		case INC -> {
			IncomeStatementDTO incomeStatement = new IncomeStatementDTO();
			incomeStatement.setSummary(instrumentList);
			company.setIncomeStatement(incomeStatement);
		}
		case BAL -> {
			BalanceSheetDTO balanceSheet = new BalanceSheetDTO();
			balanceSheet.setSummary(instrumentList);
			company.setBalanceSheet(balanceSheet);
		}

		case CAS -> {
			CashFlowStatementDTO cashFlowStatement = new CashFlowStatementDTO();
			cashFlowStatement.setSummary(instrumentList);
			company.setCashFlowStatement(cashFlowStatement);
		}
		}
	}

	}








	