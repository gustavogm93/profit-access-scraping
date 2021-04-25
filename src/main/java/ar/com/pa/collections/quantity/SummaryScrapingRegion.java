package ar.com.pa.collections.quantity;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import ar.com.pa.collections.region.RegionProp;
import ar.com.pa.collections.summary.SummaryCountry;
import lombok.AllArgsConstructor;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
@Document(collection = "summary-scraping-region")
@AllArgsConstructor
public class SummaryScrapingRegion {
	
	@Id
	private String _id;
	
	@Field(name = "region")
	private @NotNull RegionProp region;
	
	@Field(name = "countries")
	private @NotNull List<SummaryCountry> countries;
	
	@Field(name = "createdAt")
	private Date createdAt;
	
	@Field(name = "LastModifiedAt")
	private Date lastModifiedAt;
}
