package ar.com.pa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.com.pa.model.dto.MarketIndexDTO;


@Repository
public interface MarketIndexRepository  extends MongoRepository<MarketIndexDTO, String>{ 

}
