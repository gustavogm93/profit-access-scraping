package ar.com.pa.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepositoryFilter {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public CompanyRepositoryFilter( MongoTemplate mongoTemplate ) {
		this.mongoTemplate = mongoTemplate;
	}

	public List<CompanyDTO> findBy( String column , String value ) {
		Query query = new Query();
		Criteria columnCriteria = Criteria.where( column ).is( value );
		query.addCriteria( columnCriteria );
		return this.mongoTemplate.find( query , CompanyDTO.class );
	}
	

	
	
}


