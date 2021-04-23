package ar.com.pa.collections.summary;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SummaryScrapingRegionRepository extends MongoRepository<SummaryScrapingRegion, String>{}
