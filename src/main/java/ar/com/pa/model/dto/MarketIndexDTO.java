package ar.com.pa.model.dto;

import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ar.com.pa.model.props.MarketIndex;
import ar.com.pa.model.props.Share;
import lombok.Data;

@Document(collection = "MarketIndex")
@Data
public class MarketIndexDTO {
	
	@Id
	private String id;
	
	@Field(name = "CountryId")
	private String countryId;
	
	@Field(name = "propierties")
	private MarketIndex propierties;
	
	@Field(name = "Shares")
	private Set<Share> shares;
	
	public MarketIndexDTO(String id,String countryId, MarketIndex propierties, Set<Share> shares) {
		this.id = id;
		this.countryId = countryId;
		this.propierties = propierties;
		this.shares = shares;
	}



}
