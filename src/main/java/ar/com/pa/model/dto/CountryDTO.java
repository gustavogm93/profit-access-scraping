package ar.com.pa.model.dto;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import ar.com.pa.model.props.Country;
import ar.com.pa.model.props.MarketIndex;
import ar.com.pa.model.props.Region;
import ar.com.pa.model.props.Share;
import lombok.Data;

@Document(collection = "Country")
@Data
public class CountryDTO {

	@Id
	private String id;

	@Field(name = "country")
	private final Country country;

	@Field(name = "region")
	private final Region region;

	@Field(name = "Shares")
	private Set<Share> shares;
	
	@Field(name = "MarketIndex")
	private final Set<MarketIndex> marketIndexList;

	public CountryDTO(String id, Country country, Region region,Set<Share> shares ,Set<MarketIndex> marketIndexList) {
		this.id = id;
		this.country = country;
		this.region = region;
		this.shares = shares;
		this.marketIndexList = marketIndexList;
	}

}
