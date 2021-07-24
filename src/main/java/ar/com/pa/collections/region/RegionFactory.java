package ar.com.pa.collections.region;

import ar.com.pa.collections.country.CountryProp;

import java.util.Set;

public class RegionFactory {

	
	public RegionDTO build(RegionProp property, Set<CountryProp> countries) {

		RegionDTO regionDTO = new RegionDTO(property.getCode(), property, countries);
		
		return regionDTO;
	}
	
	
}
