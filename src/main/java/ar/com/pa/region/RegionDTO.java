package ar.com.pa.region;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import ar.com.pa.country.CountryProp;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Document(collection = "Region")
@AllArgsConstructor
@Data
public class RegionDTO  {
	
	@Id
	private final String id;
	
	@Field(name = "properties")
	@NonNull private final RegionProp properties;
	
	@Field(name = "countries")
	@NonNull private final Set<CountryProp> countries;

	public String getTitle() {
		return this.properties.getTitle();
	}

	

}