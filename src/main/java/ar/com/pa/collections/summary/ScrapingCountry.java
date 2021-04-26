package ar.com.pa.collections.summary;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
@AllArgsConstructor
@Document(collection = "country-scraping-data")
public class ScrapingCountry {
	
	@Id
	private String _id;
	
	@Field(name = "country")
	@NotNull private ScrapedData country;
	
	@Field(name = "market-Index-list")
	@NotNull private List<ScrapedData> marketIndexList;
	
	@Field(name = "share-list")
	@NotNull private List<ScrapedData> shares;
	
	@Field(name = "coverage")
	@NotNull private int coverage = 0;
	
	@Field(name = "createdAt")
	@NotNull private Date createdAt;
	
	@Field(name = "lastModifiedAt")
	@NotNull private Date lastModifiedAt;
	

}
