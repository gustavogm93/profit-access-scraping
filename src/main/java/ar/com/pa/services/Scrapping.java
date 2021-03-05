package ar.com.pa.services;

import java.sql.Date;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import ar.com.pa.enums.utils.UrlPattern;
import ar.com.pa.model.Instrument;
import ar.com.pa.model.financialsummary.Summary;

public interface Scrapping <T extends Summary>{ 

	//public void saveSummary(Elements elements, Elements periods, Company<T> company);
	
	public void getSummaryByPeriod(Elements e);
	
	public List<Date> getQuarterPeriodDate(Elements p);
	
	public Instrument fillInstrument(String instrumentValue);
	
	public String getUrl(Document document, UrlPattern period);
}
