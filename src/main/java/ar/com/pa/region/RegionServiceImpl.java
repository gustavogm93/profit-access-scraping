package ar.com.pa.region;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

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
		
		Criteria columnCriteria = Criteria.where("properties.title").is(title);
		
		query.addCriteria(columnCriteria);
		
		return this.mongoTemplate.find(query, RegionDTO.class);
	}

}
