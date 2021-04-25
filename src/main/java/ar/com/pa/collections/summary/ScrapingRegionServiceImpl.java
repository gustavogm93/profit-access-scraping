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
public class ScrapingRegionServiceImpl implements ScrapingRegionService {

	private final ScrapingRegionRepository ScrapingRegionRepository;

	private final MongoTemplate mongoTemplate;

	
	public List<ScrapingRegion> getAll() {
		return ScrapingRegionRepository.findAll();
	}

	public void add(ScrapingRegion ShareDTO) {
		ScrapingRegionRepository.save(ShareDTO);
	}
	
	public void addAll(List<ScrapingRegion> ShareDTOList) {
		ScrapingRegionRepository.saveAll(ShareDTOList);
	}
	
	public void delete(String code) {
		if(!ScrapingRegionRepository.existsById(code)) {
			throw new ShareNotFoundException(
                    "Share index with id " + code + " does not exists");
		}
			
		ScrapingRegionRepository.deleteById(code);
	}
	
	public List<ScrapingRegion> findByTitle(String title) {
		Query query = new Query();
		
		Criteria columnCriteria = Criteria.where("properties.title").is(title);
		
		query.addCriteria(columnCriteria);
		
		return this.mongoTemplate.find(query, ScrapingRegion.class);
	}

}
