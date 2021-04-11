package ar.com.pa.model.dto;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ar.com.pa.model.constant.SummaryCountryData;
import ar.com.pa.model.props.Region;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import static com.google.common.base.Preconditions.checkNotNull;

@Data
@Document(collection = "SummaryRegionData")
public class SummaryRegionDataDTO {
	
	@Id
	String _id;
	
	@Field(name = "Region")
	Region region;
	
	@Field(name = "Countries")
	Set<SummaryCountryData> countries;

	public SummaryRegionDataDTO(Region region, Set<SummaryCountryData> countries) {
		this.region = checkNotNull(region);
		this.countries = checkNotNull(countries);
	}
		
	
}
