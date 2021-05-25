package ar.com.pa.collections.coverage;

import java.util.List;

public interface CoverageCountryService {

	public List<CoverageCountry> getAll();

	public void add(CoverageCountry coverageCountry);
	
	public void addAll(List<CoverageCountry> coverageCountry);
	
	public void delete(String code);
	
	public List<CoverageCountry> findByTitle(String title);
	
	public List<CoverageCountry> findByCode(String title);
	
}
