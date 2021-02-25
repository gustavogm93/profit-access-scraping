package ar.com.pa.enums.utils;

public enum UrlPattern {
	
	UrlPeriod("https://www.investing.com/instruments/Financials/changesummaryreporttypeajax?action=change_report_type&pid=#&financial_id=#&ratios_id=#&period_type="),
	YearPeriod("Annual"),
	QuarterPeriod("Interim");

	private String pattern;
	
	UrlPattern(String pattern){this.pattern = pattern;}

	public String getPattern() {return pattern;}
	
}
