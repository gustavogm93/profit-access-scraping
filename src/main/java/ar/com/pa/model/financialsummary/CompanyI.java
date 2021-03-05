package ar.com.pa.model.financialsummary;

import org.springframework.stereotype.Component;

@Component
public interface CompanyI <T extends Summary>{

	public void setSummaryYear(T summaryYear);

	public void setSummaryQuarter(T summaryQuarter);
}
