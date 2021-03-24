package ar.com.pa.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Document(collection = "Share")
@Component
public class ShareDTO {
	
	@MongoId(targetType = FieldType.STRING)
	private String id;
	private String title;
		
}
