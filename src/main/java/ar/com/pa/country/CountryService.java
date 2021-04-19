package ar.com.pa.country;

import java.util.List;

public interface CountryService {

	public List<CountryDTO> getAll();

	public void add(CountryDTO countryDTO);
	
	public void addAll(List<CountryDTO> countryDTO);
	
	public void delete(String code);
			
	public List<CountryDTO> findByTitle(String title);
}
