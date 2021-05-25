package ar.com.pa.collections.coverage;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ar.com.pa.collections.share.ShareNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CoverageCountryServiceImpl implements CoverageCountryService {

	private final CoverageCountryRepository coverageCountryRepository;

	private final MongoTemplate mongoTemplate;

	
	public List<CoverageCountry> getAll() {
		return coverageCountryRepository.findAll();
	}

	public void add(CoverageCountry coverageCountry) {
		coverageCountryRepository.save(coverageCountry);
	}
	
	public void addAll(List<CoverageCountry> ShareDTOList) {
		coverageCountryRepository.saveAll(ShareDTOList);
	}
	
	public void delete(String code) {
		if(!coverageCountryRepository.existsById(code)) {
			throw new ShareNotFoundException(
                    "Share index with id " + code + " does not exists");
		}
			
		coverageCountryRepository.deleteById(code);
	}
	
	public List<CoverageCountry> findByTitle(String title) {
		Query query = new Query();
		
		Criteria columnCriteria = Criteria.where("properties.title").regex(title,"i");
		
		query.addCriteria(columnCriteria);
		
		return this.mongoTemplate.find(query, CoverageCountry.class);
	}

	public List<CoverageCountry> findByCode(String code) {
		Query query = new Query();
		
		Criteria columnCriteria = Criteria.where("_id").regex(code,"i");
		
		query.addCriteria(columnCriteria);
		
		return this.mongoTemplate.find(query, CoverageCountry.class);
	}

}
