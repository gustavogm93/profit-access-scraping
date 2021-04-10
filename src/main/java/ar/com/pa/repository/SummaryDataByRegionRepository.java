package ar.com.pa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.com.pa.model.dto.SummaryRegionDataDTO;


@Repository
public interface SummaryDataByRegionRepository extends MongoRepository<SummaryRegionDataDTO, String>{

}
