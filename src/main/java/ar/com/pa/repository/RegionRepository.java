package ar.com.pa.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.com.pa.model.RegionDTO;

@Repository
public interface RegionRepository extends MongoRepository<RegionDTO, String>{

	Optional<RegionDTO> findById(String id);
	
	RegionDTO save(String id);
}
