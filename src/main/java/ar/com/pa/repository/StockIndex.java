package ar.com.pa.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ar.com.pa.model.MarketIndex;
import ar.com.pa.model.MarketIndexDTO;

@Repository
public interface StockIndex extends MongoRepository<MarketIndexDTO, String>{ 

	Optional<MarketIndexDTO> findById(String id);
	
	MarketIndexDTO save(String id);
}
