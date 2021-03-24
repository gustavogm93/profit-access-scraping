package ar.com.pa.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.stereotype.Component;
import lombok.Data;

@Document(collection = "MarketIndex")
@Component
@Data
public class MarketIndexDTO {
	
	@MongoId(targetType = FieldType.STRING)
	private String id;
	private String title;
	private String countryCode;
	private List<ShareDTO> stockIndex;
}
