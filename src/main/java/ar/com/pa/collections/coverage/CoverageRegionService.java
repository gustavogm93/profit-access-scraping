package ar.com.pa.collections.coverage;

import ar.com.pa.collections.country.CountryProp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CoverageRegionService {

	public List<CoverageRegion> getAll();

	public void add(CoverageRegion coverageRegion);
	
	public void addAll(List<CoverageRegion> coverageRegion);
	
	public void delete(String code);
	
	public List<CoverageRegion> findByTitle(String title);
	
	public List<CoverageRegion> findByCode(String title);
	
	public List<CountryProp> getNotCoveredCountriesFromCoverageRegion(String regionCode) throws Exception ;
}
