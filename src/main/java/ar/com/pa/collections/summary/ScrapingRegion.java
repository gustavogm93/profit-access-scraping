package ar.com.pa.collections.summary;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
@Document(collection = "region-scraping-data")
public class ScrapingRegion {

	@Id
	private final String id;
	
	@Field(name = "region")
	@NotNull private ScrapedData region;
	
	@Field(name = "countries")
	@NotNull private List<ScrapedCoverageData> countries;
	
	@Field(name = "coverageTotal")
	private Integer coverageTotal = 0;
	
	@Field(name = "createdAt")
	@NotNull private Date createdAt;
	
	@Field(name = "LastModifiedAt")
	@NotNull private Date lastModifiedAt;

	public ScrapingRegion(String id, ScrapedData region, List<ScrapedCoverageData> countries) {
		this.id = id;
		this.region = region;
		this.countries = countries;
		this.createdAt = new Date();
		this.lastModifiedAt = new Date();
	}
	

	
}
