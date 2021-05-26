package ar.com.pa.collections.region;

import java.util.Set;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ar.com.pa.collections.country.CountryProp;
import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.NonNull;

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
	private int coverage;

	public String getTitle() {
		return this.properties.getTitle();
	}

	public RegionDTO(String id, @NonNull RegionProp properties, @NonNull Set<CountryProp> countries) {
		super();
		this.id = id;
		this.properties = properties;
		this.countries = countries;
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
            
            if (other.properties.getCode() != this.properties.getCode())
                return false;
            
            
        return true;
    }

}