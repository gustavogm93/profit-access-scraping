package ar.com.pa.enums.utils;

public enum UrlPattern {
	
	UrlPeriod("https://www.investing.com/instruments/Financials/changesummaryreporttypeajax?action=change_report_type&pid=#&financial_id=#&ratios_id=#&period_type="),
	YearPeriod("Annual"),
	QuarterPeriod("Interim");

	private String text;
	
	UrlPattern(String pattern){this.text = pattern;}

	public String getText() {return text;}
	
	public static boolean isAnnual(UrlPattern s){
	return UrlPattern.YearPeriod.equals(s);
	}
	
	
}
