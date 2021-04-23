package ar.com.pa.collections.country;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CountryServiceImpl implements CountryService {

	private final CountryRepository countryRepository;

	private final MongoTemplate mongoTemplate;
	
	
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
}
