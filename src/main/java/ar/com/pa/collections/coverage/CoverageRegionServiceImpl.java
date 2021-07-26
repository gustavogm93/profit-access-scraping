package ar.com.pa.collections.coverage;

import ar.com.pa.collections.country.CountryProp;
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
public class CoverageRegionServiceImpl { //implements CoverageRegionService {
/*
	private final CoverageRegionRepository coverageRegionRepository;

	private final MongoTemplate mongoTemplate;
	
	private RegionService regionService;
	
	private static final Integer COVERAGE_ACCEPTED = 100;
	
	private static final Logger log = LoggerFactory.getLogger(CoverageRegionServiceImpl.class);
	
	public List<CoverageRegion> getAll() {
		return coverageRegionRepository.findAll();
	}

	public void add(CoverageRegion coverageCountry) {
		coverageRegionRepository.save(coverageCountry);
	}
	
	public void addAll(List<CoverageRegion> coverageRegionList) {
		coverageRegionRepository.saveAll(coverageRegionList);
	}
	
	public void delete(String code) {
		if(!coverageRegionRepository.existsById(code)) {
			throw new ShareNotFoundException(
                    "Coverage Region index with id " + code + " does not exists");
		}
			
		coverageRegionRepository.deleteById(code);
	}
	
	public List<CoverageRegion> findByTitle(String title) {
		Query query = new Query();
		
		Criteria columnCriteria = Criteria.where("properties.title").regex(title,"i");
		
		query.addCriteria(columnCriteria);
		
		return this.mongoTemplate.find(query, CoverageRegion.class).stream().collect(ImmutableList.toImmutableList());
	}

	public List<CoverageRegion> findByCode(String code) {
		Query query = new Query();
		
		Criteria columnCriteria = Criteria.where("_id").regex(code,"i");
		
		query.addCriteria(columnCriteria);
		
		return this.mongoTemplate.find(query, CoverageRegion.class).stream().collect(ImmutableList.toImmutableList());
	}
	
	//TODO: Testing
	public List<CountryProp> getNotCoveredCountriesFromCoverageRegion(String regionCode) throws Exception {
		CoverageRegion coverageRegion;
		
		log.info("Getting countries in Region: {}", regionCode);

		if(regionService.verifyIfExistByCode(regionCode) == false) 
			return Collections.emptyList();
			//throw new Exception("Region doesn't exist, pull from investment through scraping region");
		
	//FACTORY SIMPLE Y COMPLETA DEPENDIENDO DEL COVERAGE | TEMPORAL O NO	
		List<CoverageRegion> optionalCoverageRegion = this.findByTitle(regionCode);
				
		if(optionalCoverageRegion.isEmpty()) 
			throw new Exception("Coverage Region doesn't exist, pull from investment through scraping coverage");
		
		 coverageRegion = optionalCoverageRegion.get(0);
		 

		 if(verifyIfRegionIsCovered(coverageRegion) == false)
				throw new Exception("Covered Region");

		
		List <CountryProp> countries = getCountriesUncoveraged(coverageRegion); 
				
		if(countries.isEmpty())
			throw new Exception("countries are covered");
		
		
		return countries;

	}

	
	private boolean verifyIfRegionIsCovered(CoverageRegion coverageRegion) {
		return coverageRegion.getCoverage() == COVERAGE_ACCEPTED;
	}
	
	private ImmutableList<CountryProp> getCountriesUncoveraged(CoverageRegion coverageRegion) {
		ImmutableList<CountryProp> countries = coverageRegion.getCountries().stream()
				.filter(withoutCoverage)
				.map(CoverageCountry::getProperties)
				.collect(ImmutableList.toImmutableList());
		
		return countries;
	}
	
	
	
	
*/
	

}
