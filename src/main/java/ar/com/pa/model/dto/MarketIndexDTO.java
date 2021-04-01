package ar.com.pa.model.dto;

import java.util.List;

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
	private List<Share> shares;
	
	public MarketIndexDTO(String countryId, MarketIndex propierties, List<Share> shares) {
		this.countryId = countryId;
		this.propierties = propierties;
		this.shares = shares;
	}



}