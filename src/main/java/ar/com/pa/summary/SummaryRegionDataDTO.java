package ar.com.pa.summary;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import ar.com.pa.region.RegionProp;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import static com.google.common.base.Preconditions.checkNotNull;

@Data
@Document(collection = "SummaryRegionData")
public class SummaryRegionDataDTO {
	
	@Id
	String _id;
	
	@Field(name = "Region")
	RegionProp region;
	
	@Field(name = "Countries")
	Set<SummaryCountryData> countries;

	public SummaryRegionDataDTO(RegionProp region, Set<SummaryCountryData> countries) {
		this.region = checkNotNull(region);
		this.countries = checkNotNull(countries);
	}
		
	
}
