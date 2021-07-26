package ar.com.pa.collections.coverage;

import ar.com.pa.collections.country.CountryProp;
import ar.com.pa.collections.region.RegionProp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
public class CoverageRegion {

	@Id
	private String id;

	@Field(name = "properties")
	@NonNull private RegionProp properties;
	
	@Field(name = "countries")
	private Integer totalCountries;

	@Field(name = "coverageCountries")
	private Integer coverageCountries;

	@Field(name = "totalCoverage")
	private Integer totalCoverage;

	@Field(name = "scrapedAt")
	private Date scrapedAt;

	public CoverageRegion(Integer totalCountries) {
		this.totalCountries = totalCountries;
		this.scrapedAt = new Date();
	}

	public static CoverageRegion build(Integer countriesQuantity){
		CoverageRegion coverageRegion = new CoverageRegion(countriesQuantity);
		return coverageRegion;
	}

	public void setCoverageCountries(Integer countryCoveragedQuantity){
		this.coverageCountries = countryCoveragedQuantity * 100 / this.totalCoverage;
	}


}
