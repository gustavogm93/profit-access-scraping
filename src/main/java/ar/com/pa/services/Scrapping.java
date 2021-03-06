package ar.com.pa.services;

import java.sql.Date;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import ar.com.pa.enums.utils.ScrappingConstant;
import ar.com.pa.enums.utils.Summaries;
import ar.com.pa.model.CompanyOperation;
import ar.com.pa.model.Instrument;
import ar.com.pa.model.financialsummary.Summary;

public interface Scrapping{ 

	//public void saveSummary(Elements elements, Elements periods, Company<T> company);
	
	public void getSummaryByPeriod(Elements e);
	
	public List<Date> getQuarterPeriodDate(Elements p);
	
	public Instrument fillInstrument(String instrumentValue);
	
	public String getUrl(Document document, Summaries period);
		
	public  String generateSummaryUrl(String codeCompany, Summaries summaryCode);

	public String generateCompanyCodeUrl(String companyTitle);
	
	public String getCompanyCode(CompanyOperation companyOperation, Summaries summaryCode);
	
	public Elements getElementsByTag(String urlSummary, String tag);
}
