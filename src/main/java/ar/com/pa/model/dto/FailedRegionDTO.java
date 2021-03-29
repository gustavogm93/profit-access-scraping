package ar.com.pa.model.dto;

import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import ar.com.pa.model.props.Country;
import ar.com.pa.model.props.Region;
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
