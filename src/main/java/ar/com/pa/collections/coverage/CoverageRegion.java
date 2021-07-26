package ar.com.pa.collections.coverage;

import ar.com.pa.collections.region.RegionProp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
public class CoverageRegion {

	@Id
	@NonNull private final String id;

	@Field(name = "properties")
	@NonNull private RegionProp properties;
	
	@Field(name = "countries")
	private Integer totalCountries;
	
	@Field(name = "coverage")
	private Integer coverage;

	@Field(name = "scrapedAt")
	@NonNull
	private Date scrapedAt;
}

//Country id solo aca y tabla separada para countries
//TODO:CUANDO SCRAPING REGION GUARDAR LOS COVERAGES
//TODO:Transactions