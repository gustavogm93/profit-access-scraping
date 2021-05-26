package ar.com.pa.collections.coverage;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Document(collection = "coverage-region-constant")
@AllArgsConstructor
@Data
public class CoverageRegion {

	@Id
	@NonNull private final String id;

	@Field(name = "title")
	@NonNull private final String title;
	
	@Field(name = "countries")
	private List<CoverageCountry> countries;
	
	@Field(name = "coverage")
	private Integer coverage;
	
	@Field(name = "lastUpdate")
	@NonNull private Date lastUpdate;
}
