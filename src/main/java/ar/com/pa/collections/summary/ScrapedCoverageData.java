package ar.com.pa.collections.summary;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.NonNull;

@Data
public class ScrapedCoverageData extends ScrapedData{

	@Field(name = "coverage")
	private Integer coverage = 0;
	
	@Field(name = "times-scraping")
	private Integer timesScraping = 0;
	
	public ScrapedCoverageData(@NonNull String code, @NonNull String title) {
		super(code, title);
	}

}
