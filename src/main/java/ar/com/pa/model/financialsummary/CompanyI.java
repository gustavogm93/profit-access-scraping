package ar.com.pa.model.financialsummary;


public interface CompanyI <T extends Summary>{

	public void setSummaryYear(T summaryYear);

	public void setSummaryQuarter(T summaryQuarter);
}
