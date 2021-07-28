package ar.com.pa.collections.coverage;

import ar.com.pa.collections.country.CountryProp;
import ar.com.pa.collections.country.CountryService;
import ar.com.pa.collections.region.RegionService;
import ar.com.pa.collections.share.ShareNotFoundException;
import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@AllArgsConstructor
@Service
public class CoverageRegionServiceImpl {

    private CountryService countryService;
    private RegionService regionService;

    public static CoverageRegion createCoverageRegion(Integer totalCountries){
        return new CoverageRegion(totalCountries);
    }

    public static CoverageRegion createCoverage(Integer countriesQuantity){
        CoverageRegion coverageRegion = new CoverageRegion(countriesQuantity);

        return coverageRegion;
    }
    //String regionToUpdate, String regionToCompare
    public static CoverageRegion updateCoverage(String regionToUpdate, String regionToCompare){
        CoverageRegion coverageRegion = new CoverageRegion(countriesQuantity);

        return coverageRegion;
    }


}
