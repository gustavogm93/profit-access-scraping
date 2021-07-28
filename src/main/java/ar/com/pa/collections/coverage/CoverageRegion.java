package ar.com.pa.collections.coverage;

import ar.com.pa.collections.country.CountryProp;
import ar.com.pa.collections.region.RegionDTO;
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

	@Field(name = "totalCoverage")
	private Integer totalCoverage;

	@Field(name = "firstScraping")
	@NonNull
	private Boolean firstScraping;

	@Field(name = "scrapedAt")
	private Date scrapedAt;

	CoverageRegion(@NonNull RegionProp properties, Integer totalCountries) {
		this.properties = properties;
		this.totalCountries = totalCountries;
		this.scrapedAt = new Date();
	}

	public static CoverageRegion createCoverage(@NonNull RegionProp properties, Integer countriesQuantity){
		return new CoverageRegion(properties, countriesQuantity);
	}

	public void setTotalCoverage(Integer countryCoveragedQuantity){
		this.totalCoverage = countryCoveragedQuantity * 100 / this.totalCoverage;
	}

}
