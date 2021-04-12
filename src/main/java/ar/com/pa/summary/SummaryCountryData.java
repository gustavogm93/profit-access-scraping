package ar.com.pa.summary;

import java.util.Set;

import ar.com.pa.country.Country;
import lombok.Data;

@Data
public class SummaryCountryData {

	private Country Country;
	private Set<SummaryMarketIndexData> summaryMarketIndex;

	public SummaryCountryData(ar.com.pa.country.Country country, Set<SummaryMarketIndexData> summaryMarketIndex) {
		Country = country;
		this.summaryMarketIndex = summaryMarketIndex;
	}
}
