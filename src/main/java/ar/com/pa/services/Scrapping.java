package ar.com.pa.services;

import java.sql.Date;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ar.com.pa.enums.utils.UrlPattern;
import ar.com.pa.model.Instrument;


public interface Scrapping { 

	public void saveSummary(Elements value, Elements periodDate);	

	public void getSummaryByPeriod(Elements e);
	
	public List<Date> getQuarterPeriodDate(Elements p);
	
	public Instrument fillInstrument(String instrumentValue);
	
	public String getUrl(Document document, UrlPattern period);
}
