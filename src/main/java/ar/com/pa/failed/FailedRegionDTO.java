package ar.com.pa.failed;

import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import ar.com.pa.collections.country.CountryProp;
import ar.com.pa.collections.region.RegionProp;
import lombok.Data;

@Document(collection = "Failed")
@Data
public class FailedRegionDTO {
	
	@Id
	private String id;
	
	@Field(name = "region")
	private final RegionProp region;
	
	@Field(name = "countries")
	private final Set<CountryProp> countries;

	public FailedRegionDTO(String id, RegionProp region, Set<CountryProp> countries) {
		this.id = id;
		this.region = region;
		this.countries = countries;
	}

}
