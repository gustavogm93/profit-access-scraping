package ar.com.pa.collections.coverage;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Data;

@Document(collection = "coverage-country-constant")
@AllArgsConstructor
@Data
public class CoverageCountry {

	@Id
	private final String id;

	@Field(name = "title")
	private final String title;
	
	@Field(name = "coverageMarketIndex")
	private final List<CoverageMarketIndex> coverageMarketIndex;
	
	@Field(name = "shareTotalQuantity")
	private final Integer sharesTotalQuantity;
	

}
