package ar.com.pa.collections.coverage;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CoverageMarketIndex {

	private final String id;

	private final String title;
	
	private final Integer totalShares; 
	
}
