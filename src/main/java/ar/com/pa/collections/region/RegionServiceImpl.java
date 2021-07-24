package ar.com.pa.collections.region;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RegionServiceImpl implements RegionService {

	private final RegionRepository regionRepository;

	private final MongoTemplate mongoTemplate;

	
	public List<RegionDTO> getAll() {
		return regionRepository.findAll();
	}

	public void add(RegionDTO regionDTO) {
		regionRepository.save(regionDTO);
	}

	public void addAll(List<RegionDTO> regionDTO) {
		regionRepository.saveAll(regionDTO);
	}
	
	public void delete(String code) {
		if (!regionRepository.existsById(code)) {
			throw new RegionNotFoundException("Region with id " + code + " does not exists");
		}

		regionRepository.deleteById(code);
	}

	public List<RegionDTO> findByTitle(String title) {
		Query query = new Query();
		
		Criteria columnCriteria = Criteria.where("properties.title").regex(title,"i");
		
		query.addCriteria(columnCriteria);
		
		return this.mongoTemplate.find(query, RegionDTO.class);
	}

	public List<RegionDTO> findByCode(String code) {
		Query query = new Query();
		
		Criteria columnCriteria = Criteria.where("properties.code").is(code);
		
		query.addCriteria(columnCriteria);
		
		return this.mongoTemplate.find(query, RegionDTO.class);
		
	}

	public boolean verifyIfExistByCode(String regionCode) {
		Optional<RegionDTO> optionalRegion = this.findByCode(regionCode).stream().findFirst();
		
		return optionalRegion.isPresent(); 
	}
	
	
	

}
