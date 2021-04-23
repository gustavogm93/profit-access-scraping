package ar.com.pa.service;

import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.pa.collections.region.RegionDTO;
import ar.com.pa.collections.region.RegionService;
import ar.com.pa.scraping.ScrapingRegion;
import junit.framework.TestCase;

@SpringBootTest
public class ScrapingRegionConstantTest extends TestCase {

	@Autowired
	private ScrapingRegion scrapingRegion;

	@Autowired
	private RegionService regionService;
	
	@Test
	public void executorTest() throws IOException {

		scrapingRegion.executor();
		
		List<RegionDTO> regionList = regionService.getAll();	
		
		boolean countriesPerRegionNotEmpty = regionList.stream().map(RegionDTO::getCountries)
																.allMatch(countriesList -> countriesList.size() > 0);
		
		Assert.assertTrue(regionList.size() > 0);
		Assert.assertTrue(countriesPerRegionNotEmpty);
	}

}
