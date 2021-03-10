package ar.com.pa.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ar.com.pa.enums.utils.SummaryType;
import ar.com.pa.model.Instrument;
import ar.com.pa.model.financialsummary.Company;


public interface ScrapingFetch{ 

	public void run(List<LocalDate> summaryPeriodTime, Company company, SummaryType summaryType, Document doc);
	
	public List<Instrument> getSummaryByPeriod(Elements e,List<Instrument> instrumentList, List<LocalDate> intervalTimeList, SummaryType summaryPerYear);
	
	public List<LocalDate> getPeriods(Elements p, SummaryType summaryType);
		
	public String buildSummaryUrl(String codeCompany, SummaryType summaryCode);

	public String generateCompanyCodeUrl(String companyTitle);
	
	public String getScrapingCodeByCompanyTitle(String companyCode);
	
	public Elements getElementsByTag(String tag, Document doc);

	public Instrument fillInstrument(String instrumentValue, List<LocalDate> intervalTimeList);
	
	public void saveSummaryCompany(Company company, List<Instrument> instrumentList,SummaryType summarie);
}