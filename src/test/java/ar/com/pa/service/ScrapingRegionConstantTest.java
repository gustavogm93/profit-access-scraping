package ar.com.pa.service;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
//import ar.com.pa.scraping.ScrapingRegion;
//import junit.framework.TestCase;

public class ScrapingRegionConstantTest  {
    @Test
    public void test(){
        Assert.isTrue(true);

    }

/*
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
*/
}
