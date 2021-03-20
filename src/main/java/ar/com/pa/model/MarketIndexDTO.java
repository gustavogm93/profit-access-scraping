package ar.com.pa.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import lombok.Data;

@Document(collection = "MarketIndex")
@Component
@Data
public class MarketIndexDTO {
	
	@Id
	String id;
	String country;
	String countryCode;
	List<MarketIndex> stockIndex;
}
