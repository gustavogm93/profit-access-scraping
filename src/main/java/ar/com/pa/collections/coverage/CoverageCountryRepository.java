package ar.com.pa.collections.coverage;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CoverageCountryRepository extends MongoRepository<CoverageCountry, String>{}
