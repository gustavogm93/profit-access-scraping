package ar.com.pa.collections.country;

import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import ar.com.pa.collections.marketIndex.MarketIndexProp;
import ar.com.pa.collections.region.RegionProp;
import ar.com.pa.collections.share.ShareProp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;


@Document(collection = "country-constant")
@AllArgsConstructor
@Data
public class CountryDTO {

	@Id
	private final String id;

	@Field(name = "country")
	@NonNull private final CountryProp properties;

	@Field(name = "region")
	@NonNull private final RegionProp region;

	@Field(name = "Shares")
	@NonNull private final Set<ShareProp> shares;
	
	@Field(name = "MarketIndex")
	@NonNull private final Set<MarketIndexProp> marketIndexList;

}
