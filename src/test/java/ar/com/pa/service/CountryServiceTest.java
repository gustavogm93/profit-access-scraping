package ar.com.pa.service;

import ar.com.pa.collections.country.*;
import ar.com.pa.collections.marketIndex.MarketIndexProp;
import ar.com.pa.collections.region.RegionProp;
import ar.com.pa.collections.share.ShareProp;
import lombok.NonNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class CountryServiceTest {

    @Autowired
    CountryServiceImpl countryService;

    @MockBean
    private CountryRepository repository;
    @Test
    public void awaitAsync() throws Exception {

    }
}
