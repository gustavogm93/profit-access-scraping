package ar.com.pa.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ar.com.pa.model.MarketIndex;
import ar.com.pa.model.company.Company;

@Repository
public interface StockIndex extends MongoRepository<MarketIndex, String>{ 

	Optional<MarketIndex> findById(String id);
	
	Company save(String id);
}
