package ar.com.pa.collections.region;

import java.util.Set;


import ar.com.pa.collections.country.CountryProp;
import ar.com.pa.generics.Property;

public class RegionFactory {

	
	public RegionDTO build(RegionProp property, Set<CountryProp> countries) {

		RegionDTO regionDTO = new RegionDTO(property.getCode(), property, countries);
		
		return regionDTO;
	}
	
	
}
