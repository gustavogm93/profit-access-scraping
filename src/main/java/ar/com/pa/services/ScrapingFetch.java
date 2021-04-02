package ar.com.pa.services;


import java.time.LocalDate;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ar.com.pa.model.company.Instrument;
import ar.com.pa.model.company.CompanyDTO;

public interface ScrapingFetch{ 

	public void run(List<LocalDate> summaryPeriodTime, CompanyDTO company, Document doc);
	
	public List<Instrument> getSummaryByPeriod(Elements e,List<Instrument> instrumentList, List<LocalDate> intervalTimeList);
	
	public List<LocalDate> getPeriods(Elements p);
		
	public String buildSummaryUrl(String codeCompany);

	public String buildProfileUrl(String companyTitle);
	
	public String buildCompanyCodeUrl(String companyTitle);
	
	public String getScrapingCodeByCompanyTitle(String companyCode);
	
	public Elements getElementsByTag(String tag, Document doc);

	public Instrument fillInstrument(String instrumentValue, List<LocalDate> intervalTimeList);
	
	public void saveSummaryCompany(CompanyDTO company, List<Instrument> instrumentList);
}
