package ar.com.pa.marketIndex;

import static com.google.common.base.Preconditions.checkNotNull;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import ar.com.pa.share.Share;

import org.springframework.data.annotation.Id;

import java.util.Comparator;
import java.util.Set;

import lombok.Data;


@Document(collection = "MarketIndex")
@Data
public class MarketIndexDTO {
	
	@Id
	private final String id;
	
	@Field(name = "CountryId")
	private final String countryId;
	
	@Field(name = "propierties")
	private final MarketIndexProp propierties;
	
	@Field(name = "Shares")
	private final Set<Share> shares;
	
	public MarketIndexDTO(String id,String countryId, MarketIndexProp propierties, Set<Share> shares) {
		this.id = checkNotNull(id);
		this.countryId = checkNotNull(countryId);
		this.propierties = checkNotNull(propierties);
		this.shares = checkNotNull(shares);
	}

	public static Comparator<MarketIndexDTO> byTitle = Comparator.comparing(MarketIndexDTO::getTitle);

	public String getTitle() {
		return this.propierties.getTitle();
	}

}
