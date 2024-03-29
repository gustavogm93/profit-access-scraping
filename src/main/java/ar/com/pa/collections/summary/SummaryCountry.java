package ar.com.pa.collections.summary;

import java.util.Set;

import javax.validation.constraints.NotNull;

import ar.com.pa.collections.country.CountryProp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SummaryCountry {

	@NotNull private CountryProp Country;
	@NotNull private Set<SummaryMarketIndexData> summaryMarketIndex;

}
