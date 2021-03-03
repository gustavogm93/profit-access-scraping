package ar.com.pa.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.com.pa.model.financialsummary.CompanyDTO;

@Repository
public interface CompanyRepository extends MongoRepository<CompanyDTO, String>{
	

	Optional<CompanyDTO> findById(String id);

	CompanyDTO save(String id);
	

}
