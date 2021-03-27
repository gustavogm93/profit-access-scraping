package ar.com.pa.model.dto;

import java.util.List;
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
	private Region properties;
	
	@Field(name = "countries")
	private List<Country> countries;

	public RegionDTO(Region properties, List<Country> countries) {
		this.properties = properties;
		this.countries = countries;
	}




	
}