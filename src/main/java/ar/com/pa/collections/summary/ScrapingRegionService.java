package ar.com.pa.collections.summary;

import java.util.List;

public interface ScrapingRegionService {

	public List<ScrapingRegion> getAll();

	public void add(ScrapingRegion ScrapingRegion);
	
	public void addAll(List<ScrapingRegion> ScrapingRegionList);
	
	public void delete(String code);
	
	public List<ScrapingRegion> findByTitle(String title);
	
}
