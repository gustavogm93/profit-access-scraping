package ar.com.pa.service;

import ar.com.pa.collections.region.RegionDTO;
import ar.com.pa.scraping.ScrapingCountryStrategy;
import com.google.common.collect.ImmutableList;
import com.mongodb.lang.Nullable;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.util.Assert.*;

public class ScrapingCountryStrategyTest {



    @Mock
    public ScrapingCountryStrategy scrapingCountryStrategy;

    @Test
    public void getRegionToFetchTest(){
        /*ImmutableList<RegionDTO> list = scrapingCountryStrategy.getRegionToFetch("regionTitle");
        assertTrue(list.size() > 0);*/
    }
}
