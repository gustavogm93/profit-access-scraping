package ar.com.pa.collections.summary;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ar.com.pa.collections.share.ShareNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ScrapingCountryServiceImpl implements ScrapingCountryService {

	private final ScrapingCountryRepository ScrapingCountryRepository;

	private final MongoTemplate mongoTemplate;

	
	public List<ScrapingCountry> getAll() {
		return ScrapingCountryRepository.findAll();
	}

	public void add(ScrapingCountry ShareDTO) {
		ScrapingCountryRepository.save(ShareDTO);
	}
	
	public void addAll(List<ScrapingCountry> ShareDTOList) {
		ScrapingCountryRepository.saveAll(ShareDTOList);
	}
	
	public void delete(String code) {
		if(!ScrapingCountryRepository.existsById(code)) {
			throw new ShareNotFoundException(
                    "Share index with id " + code + " does not exists");
		}
			
		ScrapingCountryRepository.deleteById(code);
	}
	
	public List<ScrapingCountry> findByTitle(String title) {
		Query query = new Query();
		
		Criteria columnCriteria = Criteria.where("properties.title").is(title);
		
		query.addCriteria(columnCriteria);
		
		return this.mongoTemplate.find(query, ScrapingCountry.class);
	}

	
	public List<ScrapingCountry> getAllCountriesWithoutCoverageByRegion(String regionCode){
		
		 Query query = new Query();
		 
		Criteria columnCriteria = Criteria.where("coverage").is(0).and("properties.code").is(regionCode);
		
		query.addCriteria(columnCriteria);
		
		return this.mongoTemplate.find(query, ScrapingCountry.class);
		
	}
	
	
}
