package ar.com.pa.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.pa.model.dto.FailedRegionDTO;

@Repository
public interface FailedRepository extends MongoRepository<FailedRegionDTO, String>{
		@Override
		List<FailedRegionDTO> findAll();
		
		
		@Query("{ 'region' : ?0 }")
		FailedRegionDTO findByRegionCode(String regionCode);
}
