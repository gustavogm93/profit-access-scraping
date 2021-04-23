package ar.com.pa.collections.summary;

import java.util.Set;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import ar.com.pa.collections.region.RegionProp;
import lombok.AllArgsConstructor;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
@Document(collection = "summary-scraping-data-region")
@AllArgsConstructor
public class SummaryScrapingRegion {
	
	@Id
	String _id;
	
	@Field(name = "Region")
	@NotNull RegionProp region;
	
	@Field(name = "Countries")
	@NotNull Set<SummaryCountry> countries;

}
