package ar.com.pa.collections.country;

import ar.com.pa.collections.coverage.CoverageCountry;
import ar.com.pa.collections.marketIndex.MarketIndexProp;
import ar.com.pa.collections.region.RegionProp;
import ar.com.pa.collections.share.ShareProp;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;
import java.util.function.Predicate;

@Document(collection = "country-constant")
@Data
public class CountryDTO {

	@Id
	private final String id;

	@Field(name = "country")
	private final CountryProp properties;

	@Field(name = "region")
    private final RegionProp region;

	@Field(name = "shares")
	private final Set<ShareProp> shares;
	
	@Field(name = "marketIndex")
	private final Set<MarketIndexProp> marketIndexList;

	@Field(name = "coverage")
	private CoverageCountry coverage;

	public CountryDTO(String id, CountryProp properties, RegionProp region, Set<ShareProp> shares, Set<MarketIndexProp> marketIndexList) {
		this.id = id;
		this.properties = properties;
		this.region = region;
		this.shares = shares;
		this.marketIndexList = marketIndexList;
	}


	@Override
	 public int hashCode() {
	 final int prime = 31;
	 int result = 1;
	 result = prime * result + ((id == null) ? 0 : id.hashCode());
	 return result;
	 }
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		CountryDTO other = (CountryDTO) obj;

		if (!other.id.equalsIgnoreCase(this.id))
			return false;

		return true;
	}

}
