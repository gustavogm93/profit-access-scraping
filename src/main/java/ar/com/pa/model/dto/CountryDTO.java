package ar.com.pa.model.dto;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import ar.com.pa.model.props.Country;
import ar.com.pa.model.props.MarketIndex;
import ar.com.pa.model.props.Region;
import lombok.Data;

@Document(collection = "Country")
@Data
public class CountryDTO {

	@Id
	private String id;

	@Field(name = "country")
	private Country country;

	@Field(name = "region")
	private Region region;

	@Field(name = "MarketIndex")
	private final List<MarketIndex> marketIndexList;

	public CountryDTO(String id, Country country, Region region, List<MarketIndex> marketIndexList) {
		this.id = id;
		this.country = country;
		this.region = region;
		this.marketIndexList = marketIndexList;
	}

}
