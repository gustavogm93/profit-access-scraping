package ar.com.pa.country;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import ar.com.pa.marketIndex.MarketIndexProp;
import ar.com.pa.region.RegionProp;
import ar.com.pa.share.Share;
import lombok.Data;

@Document(collection = "Country")
@Data
public class CountryDTO {

	@Id
	private final String id;

	@Field(name = "country")
	private final CountryProp country;

	@Field(name = "region")
	private final RegionProp region;

	@Field(name = "Shares")
	private final Set<Share> shares;
	
	@Field(name = "MarketIndex")
	private final Set<MarketIndexProp> marketIndexList;

	public CountryDTO(String id, CountryProp country, RegionProp region,Set<Share> shares ,Set<MarketIndexProp> marketIndexList) {
		this.id = checkNotNull(id); 
		this.country = checkNotNull(country);
		this.region = checkNotNull(region);
		this.shares = checkNotNull(shares);
		this.marketIndexList = checkNotNull(marketIndexList);
	}

}
