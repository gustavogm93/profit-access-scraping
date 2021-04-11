package ar.com.pa.model.constant;

import java.util.Set;

import ar.com.pa.model.props.Country;
import lombok.Data;

@Data
public class SummaryCountryData {

	private Country Country;
	private Set<SummaryMarketIndexData> summaryMarketIndex;

	public SummaryCountryData(ar.com.pa.model.props.Country country, Set<SummaryMarketIndexData> summaryMarketIndex) {
		Country = country;
		this.summaryMarketIndex = summaryMarketIndex;
	}
}
