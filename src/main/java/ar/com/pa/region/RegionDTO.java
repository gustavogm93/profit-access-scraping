package ar.com.pa.region;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Id;

import ar.com.pa.country.Country;
import ar.com.pa.enums.RegionConstant;
import ar.com.pa.generics.InvalidDataException;
import lombok.Data;

@Document(collection = "Region")
@Data
public class RegionDTO  {
	
	@Id
	private final String id;
	
	@Field(name = "properties")
	private final RegionProp properties;
	
	@Field(name = "countries")
	private final Set<Country> countries;

	public RegionDTO(String id, RegionProp regionProp, Set<Country> countries) throws InvalidDataException {
		verifyRegion(regionProp);
		
		this.id = checkNotNull(id);
		this.properties = checkNotNull(regionProp);
		this.countries = checkNotNull(countries);
	}

	public String getTitle() {
		return this.properties.getTitle();
	}

	
	public void verifyRegion(RegionProp regionProp) throws InvalidDataException {
		
		boolean isValidRegion = RegionConstant.values.stream()
		  .map(RegionConstant::getTitle)
		  .anyMatch(regionConstantTitle -> regionProp.getTitle().equalsIgnoreCase(regionConstantTitle));
		
		if(!isValidRegion)
			throw new InvalidDataException("invalid Region title");
				
	}
	

}