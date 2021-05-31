package ar.com.pa.collections.coverage;

import org.springframework.data.mongodb.core.mapping.Field;

import ar.com.pa.collections.marketIndex.MarketIndexProp;
import lombok.AllArgsConstructor;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@AllArgsConstructor
@Data
public class CoverageMarketIndex {
	
	@Id
	private final String id;
	
	@Field(name = "country")
	private final MarketIndexProp marketIndexProp;
	
	private final Integer totalShares; 
	
}
