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
@Document(collection = "market-Index-constant")
@Data
public class MarketIndexDTO {

	@Id
	private final String id;

	@Field(name = "CountryId")
	@NonNull
	private final String countryId;

	@Field(name = "propierties")
	@NonNull
	private final MarketIndexProp propierties;

	@Field(name = "Shares")
	@NonNull
	private final Set<ShareProp> shares;

	public String getTitle() {
		return this.propierties.getTitle();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		MarketIndexDTO other = (MarketIndexDTO) obj;

		if (other.id.equalsIgnoreCase(this.id))
			return false;

		return true;
	}
}
