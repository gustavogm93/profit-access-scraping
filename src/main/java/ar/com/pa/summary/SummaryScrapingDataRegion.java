package ar.com.pa.summary;

import java.util.Set;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ar.com.pa.region.RegionProp;
import lombok.AllArgsConstructor;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
@Document(collection = "Summary-Scraping-data-Region")
@AllArgsConstructor
public class SummaryScrapingDataRegion {
	
	@Id
	String _id;
	
	@Field(name = "Region")
	@NotNull RegionProp region;
	
	@Field(name = "Countries")
	@NotNull Set<SummaryCountry> countries;

}
