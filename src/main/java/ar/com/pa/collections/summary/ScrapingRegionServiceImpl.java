package ar.com.pa.collections.summary;

import java.util.List;
import java.util.Optional;

import org.jsoup.nodes.Element;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.google.common.base.Predicate;

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

	public void add(ScrapingRegion scrapedRegion) {
		ScrapingRegionRepository.save(scrapedRegion);
	}
	
	public void addAll(List<ScrapingRegion> scapedRegionList) {
		ScrapingRegionRepository.saveAll(scapedRegionList);
	}
	
	public void delete(String code) {
		if(!ScrapingRegionRepository.existsById(code)) {
			throw new ShareNotFoundException(
                    "Share index with id " + code + " does not exists");
		}
			
		ScrapingRegionRepository.deleteById(code);
	}
	
	public ScrapingRegion findByTitle(String title) {
		Query query = new Query();
		
		Criteria columnCriteria = Criteria.where("properties.title").is(title);
		
		query.addCriteria(columnCriteria);
		
		return this.mongoTemplate.findOne(query, ScrapingRegion.class);
	}

	
	public List<ScrapedCoverageData> getCountriesByRegionTitle(String title) {
		Query query = new Query();
		
		Criteria columnCriteria = Criteria.where("properties.title").is(title);
		
		query.addCriteria(columnCriteria);
		
		ScrapingRegion region =  this.mongoTemplate.findOne(query, ScrapingRegion.class);
		
		return region.getCountries();
	}



	
}
