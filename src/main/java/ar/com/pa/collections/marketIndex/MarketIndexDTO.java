package ar.com.pa.collections.marketIndex;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import ar.com.pa.collections.share.ShareProp;

import org.springframework.data.annotation.Id;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Document(collection = "Market-Index-constant")
@Data
public class MarketIndexDTO {
	
	@Id
	private final String id;
	
	@Field(name = "CountryId")
	@NonNull private final String countryId;
	
	@Field(name = "propierties")
	@NonNull private final MarketIndexProp propierties;
	
	@Field(name = "Shares")
	@NonNull private final Set<ShareProp> shares;


	public String getTitle() {
		return this.propierties.getTitle();
	}

}
