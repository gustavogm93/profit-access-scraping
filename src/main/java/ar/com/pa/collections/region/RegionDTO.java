package ar.com.pa.collections.region;

import ar.com.pa.collections.country.CountryProp;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

@Document(collection = "region-constant")
@Data
public class RegionDTO {

	@Id
	private final String id;

	@Field(name = "properties")
	@NonNull
	private final RegionProp properties;

	@Field(name = "countries")
	@NonNull
	private final Set<CountryProp> countries;

	@Field(name = "coverage")
	private CoverageRegion coverage;

	public String getTitle() {
		return this.properties.getTitle();
	}
	public String getCode() { return this.properties.getCode(); }

	private RegionDTO(String id, @NonNull RegionProp properties, @NonNull Set<CountryProp> countries) {
		super();
		this.id = id;
		this.properties = properties;
		this.countries = countries;
	}

	public static RegionDTO createRegion(String id, @NonNull RegionProp properties, @NonNull Set<CountryProp> countries) {
		RegionDTO region = new RegionDTO(id,properties,countries);
		CoverageRegion coverageRegion = CoverageRegion.createCoverage(properties, countries.size());
		region.setCoverage(coverageRegion);
		return region;
	}

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        
        if (obj == null)
            return false;
        
        if (getClass() != obj.getClass())
            return false;
        
        RegionDTO other = (RegionDTO) obj;

            if (other.id != this.id)
                return false;
            
            if (!other.properties.getCode().equals(this.properties.getCode()))
                return false;
            
            
        return true;
    }

    public Integer countCountries(){
		return this.countries.size();
	}

	public static Comparator<RegionDTO> byTitle = Comparator.comparing(RegionDTO::getTitle);
	
	public static Comparator<RegionDTO> byCode = Comparator.comparing(RegionDTO::getTitle);

	public static Predicate<RegionDTO> byNotCoveraged = (region) -> region.getCoverage().getTotalCoverage() < 80;

}