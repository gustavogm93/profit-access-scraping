package ar.com.pa.collections.summary;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import ar.com.pa.collections.region.RegionProp;
import lombok.AllArgsConstructor;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
@Document(collection = "summary-scraping-region")
@AllArgsConstructor
public class ScrapingRegion {
	
	@Id
	String _id;
	
	@Field(name = "region")
	@NotNull private RegionProp region;
	
	@Field(name = "countries")
	@NotNull private List<ScrapingCountry> countries;
	
	@Field(name = "createdAt")
	@NotNull private Date createdAt;
	
	@Field(name = "LastModifiedAt")
	@NotNull private Date lastModifiedAt;
}
