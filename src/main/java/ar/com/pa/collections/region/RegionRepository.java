package ar.com.pa.collections.region;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends MongoRepository<RegionDTO, String>{}
