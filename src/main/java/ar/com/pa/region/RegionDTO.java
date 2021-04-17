package ar.com.pa.region;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import ar.com.pa.country.CountryProp;

import org.springframework.data.annotation.Id;
import lombok.Data;

@Document(collection = "Region")
@Data
public class RegionDTO  {
	
	@Id
	private final String _id;
	
	@Field(name = "properties")
	private final RegionProp properties;
	
	@Field(name = "countries")
	private final Set<CountryProp> countries;

	public RegionDTO(String id, RegionProp regionProp, Set<CountryProp> countries) {
		this._id = checkNotNull(id);
		this.properties = checkNotNull(regionProp);
		this.countries = checkNotNull(countries);
	}

	public String getTitle() {
		return this.properties.getTitle();
	}

	

}