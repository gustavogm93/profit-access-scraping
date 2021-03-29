package ar.com.pa.model.dto;

import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ar.com.pa.model.props.Country;
import ar.com.pa.model.props.Region;
import lombok.Data;

@Document(collection = "Region")
@Data
public class RegionDTO {
	
	@Id
	private String id;
	
	@Field(name = "properties")
	private final Region properties;
	
	@Field(name = "countries")
	private final Set<Country> countries;

	public RegionDTO(String id, Region properties, Set<Country> countries) {
		this.id = id;
		this.properties = properties;
		this.countries = countries;
	}


}