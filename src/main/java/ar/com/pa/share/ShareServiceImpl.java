package ar.com.pa.share;


import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ShareServiceImpl implements ShareService {

	private final ShareRepository ShareRepository;

	private final MongoTemplate mongoTemplate;

	
	public List<ShareDTO> getAllShare() {
		return ShareRepository.findAll();
	}

	public void addShare(ShareDTO ShareDTO) {
		ShareRepository.save(ShareDTO);
	}
	
	public void addAllShare(List<ShareDTO> ShareDTOList) {
		ShareRepository.saveAll(ShareDTOList);
	}
	
	public void deleteShare(String code) {
		if(!ShareRepository.existsById(code)) {
			throw new ShareNotFoundException(
                    "Share index with id " + code + " does not exists");
		}
			
		ShareRepository.deleteById(code);
	}
	
	public List<ShareDTO> findByTitle(String title) {
		Query query = new Query();
		
		Criteria columnCriteria = Criteria.where("properties.title").is(title);
		
		query.addCriteria(columnCriteria);
		
		return this.mongoTemplate.find(query, ShareDTO.class);
	}
}