package ar.com.pa.collections.summary;

import java.util.List;

public interface ScrapingCountryService {

	public List<ScrapingCountry> getAll();

	public void add(ScrapingCountry ScrapingRegion);
	
	public void addAll(List<ScrapingCountry> ScrapingRegionList);
	
	public void delete(String code);
	
	public List<ScrapingCountry> findByTitle(String title);
	
	public List<ScrapingCountry> getAllCountriesWithoutCoverageByRegion(String regionCode);

}
