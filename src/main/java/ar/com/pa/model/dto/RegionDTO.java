package ar.com.pa.model.dto;

import java.util.Collections;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Id;
import ar.com.pa.model.props.Country;
import ar.com.pa.model.props.Region;
import lombok.Data;

@Document(collection = "Region")
@Data
public class RegionDTO  {
	
	@Id
	private final String id;
	
	@Field(name = "properties")
	private final Region properties;
	
	@Field(name = "countries")
	private final Set<Country> countries;

	public RegionDTO(String id, Region properties, Set<Country> countries) {
		this.id = checkNotNull(id);
		this.properties = checkNotNull(properties);
		this.countries = checkNotNull(countries);
	}

	
}