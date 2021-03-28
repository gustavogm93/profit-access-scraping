package ar.com.pa.model.dto;

import java.util.List;

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
	
	@Field(name = "properties")
	private final Region properties;
	
	@Field(name = "countries")
	private final List<Country> countries;

	public FailedRegionDTO(Region properties, List<Country> countries) {
		this.properties = properties;
		this.countries = countries;
	}

}
