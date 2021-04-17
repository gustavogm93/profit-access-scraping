package ar.com.pa.service;

import java.io.IOException;
import java.util.TreeSet;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.google.common.collect.ImmutableList;
import ar.com.pa.country.CountryProp;
import ar.com.pa.enums.RegionConstant;
import static ar.com.pa.enums.utils.Url.*;
import ar.com.pa.region.RegionDTO;
import ar.com.pa.region.RegionProp;
import ar.com.pa.scraping.ScrapingRegionConstant;
import junit.framework.TestCase;

@SpringBootTest
public class ScrapingRegionConstantTest extends TestCase {

	@Autowired
	private ScrapingRegionConstant scrapingRegionConstant;
/*
	@Test
	public void executorTest() throws IOException {
		int index = 0;
		Document document = scrapingRegionConstant.getDocument(equities);

		ImmutableList<RegionConstant> regionConstantList = scrapingRegionConstant.getConstantsToFetch();

		regionConstantList.stream().limit(index).forEach((region) -> {

			Elements countriesElements = scrapingRegionConstant.getCountriesElementsInDocument(document, region);

			TreeSet<CountryProp> countries = scrapingRegionConstant.elementsToTreeSet(countriesElements);

			RegionProp regionProps = new RegionProp(region.getCode(), region.getTitle());

			RegionDTO regionDTO = new RegionDTO(region.getCode(), regionProps, countries);

			var idRegion = regionDTO.get_id();
			var codeRegion = regionDTO.getProperties().getCode();
			var titleRegion = regionDTO.getProperties().getTitle();

			Assert.assertNotNull(idRegion);
			Assert.assertNotNull(codeRegion);
			Assert.assertNotNull(titleRegion);

			Assert.assertTrue(titleRegion.equalsIgnoreCase(regionConstantList.get(index).getTitle()));
		});

	}

	@Test
	public void getDocumentTest() throws IOException {
		Document document = scrapingRegionConstant.getDocument(equities);
		Assert.assertNotNull(document);
	}

	@Test
	public void getConstantsToFetchTest() {
		ImmutableList<RegionConstant> regionConstantList = scrapingRegionConstant.getConstantsToFetch();
		Assert.assertNotNull(regionConstantList);
		Assert.assertSame(regionConstantList, RegionConstant.values);
	}
*/
	@Test
	public void getConstantsToFetcheTest() {
		var a= ScrapingRegionConstantTest.class
	    .getClassLoader().getResource("driver/chromedriver.exe");
		
		System.out.println(a);
		
		
	}

	
	
	
}
