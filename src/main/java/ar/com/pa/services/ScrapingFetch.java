package ar.com.pa.services;

import java.sql.Date;
import java.util.List;
import java.util.Map.Entry;

import org.jsoup.select.Elements;
import ar.com.pa.enums.utils.SummaryType;
import ar.com.pa.model.Instrument;
import ar.com.pa.model.financialsummary.Company;


public interface ScrapingFetch{ 

	public void run(List<Date> summaryPeriods, Company company, SummaryType summaryType, String url);
	
	public void getSummaryByPeriod(Elements e,List<Instrument> instrumentList, List<Date> intervalTimeList, SummaryType summaryPerYear);
	
	public List<Date> getPeriods(Elements p);
		
	public Entry<SummaryType, String> buildSummaryUrl(String codeCompany, SummaryType summaryCode);

	public String generateCompanyCodeUrl(String companyTitle);
	
	public String getScrapingCodeByCompanyTitle(String companyCode);
	
	public Elements getElementsByTag(String urlSummary, String tag);

	public Instrument fillInstrument(String instrumentValue, List<Date> intervalTimeList);
	
	public void saveSummaryCompany(Company company, List<Instrument> instrumentList,SummaryType summarie);
}
