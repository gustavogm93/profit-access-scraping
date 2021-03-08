package ar.com.pa.services;


import java.io.IOException;
import java.sql.Date;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ar.com.pa.enums.financialsummary.FinancialSummary;
import ar.com.pa.enums.utils.ScrappingConstant;
import ar.com.pa.enums.utils.SummaryType;
import ar.com.pa.model.Instrument;
import ar.com.pa.model.financialsummary.Company;
import ar.com.pa.utils.MapperUtils;
import ar.com.pa.utils.PatternResource;
import ar.com.pa.utils.ValidateUtils;

@Service
public class ScrapingFetchImpl implements ScrapingFetch{

	private final Logger logger = LoggerFactory.getLogger(ScrapingFetchImpl.class);
	
    private FinancialSummary financialSummary;

    private static int dateIndex = 0;

	public ScrapingFetchImpl() {};

	public void run(List<Date> summaryPeriods, Company company, SummaryType summaryType, String url) {

		logger.info("Scrapping financial Summary into HashMap and return it");
	
		List<Instrument> instrumentsPerSummary = new ArrayList<>();
		
		Elements scrapingElements = getElementsByTag(url, "Td");
		
		getSummaryByPeriod(scrapingElements, instrumentsPerSummary, summaryPeriods, summaryType);
		
		saveSummaryCompany(company, instrumentsPerSummary, summaryType);
		
		xd(instrumentsPerSummary);
		System.out.println("----");


		//companyRepository.save(company);
		
	}
	
	@Override
	public String getScrapingCodeByCompanyTitle(String companyTitle) {

		Document doc;
		try {
			doc = Jsoup.connect(generateCompanyCodeUrl(companyTitle)).get();
			
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
		companyCode.append(companyTitle);
		
		return companyCode.toString();
		
	}
	
	@Override
	public Entry<SummaryType, String> buildSummaryUrl(String codeCompany, SummaryType summaryCode) {

		StringBuilder summaryUrl = new StringBuilder();
		
		summaryUrl.append(ScrappingConstant.fixUrl); 
		
		if(summaryCode == SummaryType.FS) 
			 summaryUrl.append(ScrappingConstant.urlFs.replace("#", codeCompany));
		else
			summaryUrl.append(ScrappingConstant.urlNotFs.replace("#", codeCompany).replace("$", summaryCode.toString()));
			 
		Entry<SummaryType, String> urlList = new AbstractMap.SimpleEntry<SummaryType, String>(summaryCode, summaryUrl.toString());
	
		return urlList;
	}
	
	
	public Entry<SummaryType, String> buildScrapingSummdaryUrl(String codeCompany, SummaryType summaryCode) {

		StringBuilder summaryUrl = new StringBuilder();
		
		summaryUrl.append(ScrappingConstant.fixUrl); 
		
		if(summaryCode == SummaryType.FS) 
			 summaryUrl.append(ScrappingConstant.urlFs.replace("#", codeCompany));
		else
			summaryUrl.append(ScrappingConstant.urlNotFs.replace("#", codeCompany).replace("$", summaryCode.toString()));
			 
		Entry<SummaryType, String> urlList = new AbstractMap.SimpleEntry<SummaryType, String>(summaryCode, summaryUrl.toString());
	
		return urlList;
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
	public void getSummaryByPeriod(Elements e,List<Instrument> instrumentList, List<Date> intervalTimeList, SummaryType summaryPerYear) {

		
		e.stream()
		.map(Element::ownText)
		.filter(ValidateUtils::isSummaryModelValue)
		.forEach((element) -> {		
			
					if (ValidateUtils.isSummaryObject(element)) {
						
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
	  

	  
	public List<Date> getPeriods(Elements p) {

		return p.stream().map(Element::ownText).distinct().filter(PatternResource::dateStringPattern)
				.map(MapperUtils::toDate).collect(Collectors.toList());

	}

	
	public void saveSummaryCompany(Company company, List<Instrument> instrumentList,SummaryType summaryType) {
	/*
		switch(summarie) {
		
		case FS->{
			FinancialSummaryDTO FinancialSummary = new FinancialSummaryDTO();
			FinancialSummary.setSummary(instrumentList);
			company.setFinancialSummary(FinancialSummary);
		}
		case INC-> {
			IncomeStatementDTO incomeStatement = new IncomeStatementDTO();
			incomeStatement.setSummary(instrumentList);
			company.setIncomeStatement(incomeStatement);
		}
		case BAL-> {
			BalanceSheetDTO balanceSheet = new BalanceSheetDTO();
			balanceSheet.setSummary(instrumentList);
			company.setBalanceSheet(balanceSheet);
		}

		case CAS ->{
			CashFlowStatementDTO cashFlowStatement = new CashFlowStatementDTO();
			cashFlowStatement.setSummary(instrumentList);
			company.setCashFlowStatement(cashFlowStatement);
		}
		}*/
	}

		
	}



	




	