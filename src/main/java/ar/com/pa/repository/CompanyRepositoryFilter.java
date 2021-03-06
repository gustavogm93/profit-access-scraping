package ar.com.pa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import ar.com.pa.model.financialsummary.Company;


@Repository
public abstract class CompanyRepositoryFilter implements CompanyRepository {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public CompanyRepositoryFilter( MongoTemplate mongoTemplate ) {
		this.mongoTemplate = mongoTemplate;
	}

	public List<Company> findBy( String column , String value ) {
		Query query = new Query();
		Criteria columnCriteria = Criteria.where( column ).is( value );
		query.addCriteria( columnCriteria );
		return this.mongoTemplate.find( query , Company.class );
	}
	

	
	
}


