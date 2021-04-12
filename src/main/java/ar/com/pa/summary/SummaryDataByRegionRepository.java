package ar.com.pa.summary;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SummaryDataByRegionRepository extends MongoRepository<SummaryRegionDataDTO, String>{

}
