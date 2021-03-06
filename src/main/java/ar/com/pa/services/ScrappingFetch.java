package ar.com.pa.services;

import java.sql.Date;
import java.util.List;
import org.jsoup.select.Elements;
import ar.com.pa.enums.utils.Summaries;
import ar.com.pa.model.CompanyOperation;
import ar.com.pa.model.Instrument;
import ar.com.pa.model.financialsummary.Company;


public interface ScrappingFetch{ 

	public void run(Elements periods, Company company, Summaries summarie );
	
	public void getSummaryByPeriod(Elements e,List<Instrument> instrumentList, List<Date> intervalTimeList, Summaries summaryPerYear);
	
	public List<Date> getQuarterPeriodDate(Elements p);
		
	public  String generateSummaryUrl(String codeCompany, Summaries summaryCode);

	public String generateCompanyCodeUrl(String companyTitle);
	
	public String getCompanyCode(CompanyOperation companyOperation, Summaries summaryCode);
	
	public Elements getElementsByTag(String urlSummary, String tag);

	public Instrument fillInstrument(String instrumentValue, List<Date> intervalTimeList);
	
	public void saveSummaryCompany(Company company, List<Instrument> instrumentList,Summaries summarie);
}
