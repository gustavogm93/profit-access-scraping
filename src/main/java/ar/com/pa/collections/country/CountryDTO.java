package ar.com.pa.collections.country;

import ar.com.pa.collections.marketIndex.MarketIndexProp;
import ar.com.pa.collections.region.RegionProp;
import ar.com.pa.collections.share.ShareProp;
import ar.com.pa.utils.GenerateUUID;
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

	private String generateId(CountryProp properties){
		return String.format("%s-%s",properties.getCode(), properties.getTitle().substring(0,2));
	}

	private CountryDTO(CountryProp properties, RegionProp region) {
		this.id = generateId(properties);
		this.properties = properties;
		this.region = region;
	}


	public static CountryDTO createNewCountry(@NonNull CountryProp properties, @NonNull RegionProp region) {
		CountryDTO country = new CountryDTO(properties,region);
		CoverageCountry coverageCountry = CoverageCountry.createNewCoverage();
		country.setCoverage(coverageCountry);
		return country;
	}

	public static CountryDTO createBaseCountry(@NonNull CountryProp properties, @NonNull RegionProp region) {
		CountryDTO country = new CountryDTO(properties,region);
		CoverageCountry coverageCountry = CoverageCountry.createNewCoverage();
		country.setCoverage(coverageCountry);
		return country;
	}


	public CountryDTO updateCountry(@NonNull CountryProp properties, @NonNull RegionProp region) {
		CountryDTO country = new CountryDTO(properties,region);
		CoverageCountry coverageCountry = CoverageCountry.createNewCoverage();
		country.setCoverage(coverageCountry);
		return country;
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

	public String getCode(){
		return this.getProperties().getCode();
	}

	public String getTitle(){
		return this.getProperties().getTitle();
	}
}
