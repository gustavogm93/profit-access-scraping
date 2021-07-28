package ar.com.pa.collections.country;

import com.google.common.collect.ImmutableList;

import java.util.List;

public interface CountryService {

	public List<CountryDTO> getAll();

	public void add(CountryDTO countryDTO);
	
	public void addAll(List<CountryDTO> countryDTO);
	
	public void delete(String code);
			
	public List<CountryDTO> findByTitle(String title);

	public List<CountryDTO> getCountriesCoveredByRegion(String region) throws Exception;

	public ImmutableList<CountryDTO> getCountriesUncoveredByRegion(String region) throws Exception;

	public ImmutableList<CountryDTO> getSeveralsCountriesUncoveredFromRegions(List<String> regions);
	public CountryDTO findByCode(String code) throws Exception;
	}
