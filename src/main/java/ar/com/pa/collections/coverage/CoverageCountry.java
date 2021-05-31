package ar.com.pa.collections.coverage;

import ar.com.pa.collections.country.CountryProp;
import com.google.common.base.Function;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

@AllArgsConstructor
@Data
public class CoverageCountry {

	@Id
	@NonNull private final String id;

	@Field(name = "country")
	private final CountryProp properties;
	
	@Field(name = "coverageMarketIndex")
	private final List<CoverageMarketIndex> coverageMarketIndex;
	
	@Field(name = "totalShares")
	@NonNull private final Integer totalShares;
	
	@Field(name = "coverage")
	@NonNull private Integer coverage;
	
	@Field(name = "lastUpdate")
	@NonNull private Date lastUpdate;



public static Predicate<CoverageCountry> withoutCoverage = (coverageCountry) -> {
	return coverageCountry.getCoverage() <= 95;
};


public static Function<CoverageCountry, CountryProp> getCountryProp = new Function<CoverageCountry, CountryProp>() {

	public CountryProp apply(CoverageCountry coverageCountry) {

		return coverageCountry.properties;
	}

};

}

//QUE TODOS TENGAN PROPERTYES COMPARTIDAS
