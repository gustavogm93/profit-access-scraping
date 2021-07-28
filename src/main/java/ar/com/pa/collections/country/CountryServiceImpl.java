package ar.com.pa.collections.country;

import ar.com.pa.utils.Validates;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@Service
public class CountryServiceImpl implements CountryService {

	private final CountryRepository countryRepository;

	private final MongoTemplate mongoTemplate;

	private final Validates validate;
	
	public List<CountryDTO> getAll() {
		return countryRepository.findAll();
	}

	public void add(CountryDTO countryDTO) {
		countryRepository.save(countryDTO);
	}
	
	public void addAll(List<CountryDTO> countryDTO) {
		countryRepository.saveAll(countryDTO);
	}
	
	public void delete(String code) {
		if(!countryRepository.existsById(code)) {
			throw new CountryNotFoundException(
                    "Country with id " + code + " does not exists");
		}
			
		countryRepository.deleteById(code);
	}
	
	public List<CountryDTO> findByTitle(String title) {
		Query query = new Query();
		
		Criteria columnCriteria = Criteria.where("properties.title").is(title);
		
		query.addCriteria(columnCriteria);
		
		return this.mongoTemplate.find(query, CountryDTO.class);
	}

	public List<CountryDTO> getCountriesCoveredByRegion(String region) throws Exception {
		Query query = new Query();
		region = region.toLowerCase();

		if(!validate.isValidRegion(region))
			throw new Exception("Invalid region");

		Criteria isRegionCriteria = Criteria.where("region.title").is(region);
		Criteria isCoveredCriteria = Criteria.where("coverage.isCovered").is(true);

		query.addCriteria(isRegionCriteria);
		query.addCriteria(isCoveredCriteria);

		return this.mongoTemplate.find(query, CountryDTO.class);
	}

	public List<CountryDTO> getCountriesUncoveredByRegion(String region) throws Exception {
		Query query = new Query();
		region = region.toLowerCase();

		if(!validate.isValidRegion(region))
			throw new Exception("Invalid region");

		Criteria isRegionCriteria = Criteria.where("region.title").is(region);
		Criteria isCoveredCriteria = Criteria.where("coverage.isCovered").is(false);

		query.addCriteria(isRegionCriteria);
		query.addCriteria(isCoveredCriteria);

		return this.mongoTemplate.find(query, CountryDTO.class);
	}
}
