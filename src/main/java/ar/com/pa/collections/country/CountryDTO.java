package ar.com.pa.collections.country;

import ar.com.pa.collections.marketIndex.MarketIndexProp;
import ar.com.pa.collections.region.RegionProp;
import ar.com.pa.collections.share.ShareProp;
import lombok.Data;
import lombok.NonNull;
import org.apache.poi.ss.formula.functions.Count;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Predicate;

@Document(collection = "country-constant")
@Data
public class CountryDTO {

	@Id
	private final String id;

	@Field(name = "country")
	@NonNull private final CountryProp properties;

	@Field(name = "region")
	@NonNull private final RegionProp region;

	@Field(name = "shares")
	private Set<ShareProp> shares;
	
	@Field(name = "marketIndex")
	private Set<MarketIndexProp> marketIndexList;

	@Field(name = "coverage")
	@NonNull private CoverageCountry coverage;

	private CountryDTO(String id, CountryProp properties, RegionProp region) {
		this.id = id;
		this.properties = properties;
		this.region = region;
	}


	public static CountryDTO createNewCountry(String id, @NonNull CountryProp properties, @NonNull RegionProp region) {
		CountryDTO country = new CountryDTO(id,properties,region);
		CoverageCountry coverageCountry = CoverageCountry.buildCoverageBaseToCompare(marketIndexList.size(), shares.size());
		country.setCoverage(coverageCountry);
		return country;
	}







	public void updateCoverage(CoverageCountry newCoverage) throws Exception {
		if(newCoverage.getIsCoverageBase())
			throw new Exception("You can't generate a Coverage from base");

		this.coverage.generateShareCoverage(newCoverage);
		this.coverage.generateMarketIndexCoverage(newCoverage);
		this.coverage.setTotalCoverage();
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

	Predicate<CountryDTO> isCovered = (c) -> c.coverage.getIsCovered();

}
