package ar.com.pa.failed;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FailedRepository extends MongoRepository<FailedRegionDTO, String>{
		@Override
		List<FailedRegionDTO> findAll();
		
		
		@Query("{ 'region' : ?0 }")
		FailedRegionDTO findByRegionCode(String regionCode);
}
