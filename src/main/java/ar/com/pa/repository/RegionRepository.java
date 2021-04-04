package ar.com.pa.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.pa.model.dto.RegionDTO;

@Repository
public interface RegionRepository extends MongoRepository<RegionDTO, String>{
		@Override
		List<RegionDTO> findAll();
		
		@Query("{ 'properties.title' : ?0 }")
		List<RegionDTO> findByTitle(String title);
}
