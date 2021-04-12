package ar.com.pa.failed;

import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import Region.Region;
import ar.com.pa.country.Country;
import lombok.Data;

@Document(collection = "Failed")
@Data
public class FailedRegionDTO {
	
	@Id
	private String id;
	
	@Field(name = "region")
	private final Region region;
	
	@Field(name = "countries")
	private final Set<Country> countries;

	public FailedRegionDTO(String id, Region region, Set<Country> countries) {
		this.id = id;
		this.region = region;
		this.countries = countries;
	}

}
