package ar.com.pa.summary;

import java.util.Set;

import ar.com.pa.country.CountryProp;
import lombok.Data;

@Data
public class SummaryCountryData {

	private CountryProp Country;
	private Set<SummaryMarketIndexData> summaryMarketIndex;

	public SummaryCountryData(CountryProp country, Set<SummaryMarketIndexData> summaryMarketIndex) {
		Country = country;
		this.summaryMarketIndex = summaryMarketIndex;
	}
}
