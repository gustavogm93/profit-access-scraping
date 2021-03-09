package ar.com.pa.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ar.com.pa.model.financialsummary.Company;


@Repository
public interface CompanyRepository extends MongoRepository<Company, String>{
	
	Optional<Company> findById(String id);
	
	Company save(String id);
	

}
