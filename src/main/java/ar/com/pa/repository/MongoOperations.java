package ar.com.pa.repository;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ar.com.pa.model.Property;


public class MongoOperations<T extends Property> {

	
	private MongoTemplate mongoTemplate;
	
	public void update(String code, T object){

		//SimpleDateFormat sf = new SimpleDateFormat( OperationManagerConstants.DATE_FORMAT );

	        Query query = new Query();
	        query.addCriteria(Criteria.where("code").is(object.getCode()));	        
	        

	}
	
}
