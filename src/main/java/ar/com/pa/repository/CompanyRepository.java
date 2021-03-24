package ar.com.pa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ar.com.pa.model.company.CompanyDTO;


@Repository
public interface CompanyRepository extends MongoRepository<CompanyDTO, String>{

}
