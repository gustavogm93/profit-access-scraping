package ar.com.pa.collections.summary;

import java.util.Comparator;

import ar.com.pa.collections.marketIndex.MarketIndexProp;
import lombok.Data;

@Data
public class ScrapingMarketIndex {

	MarketIndexProp marketIndex;
	Integer sharesQuantity;
	
	public ScrapingMarketIndex(MarketIndexProp marketIndex, Integer sharesQuantity) {
		this.marketIndex = marketIndex;
		this.sharesQuantity = sharesQuantity;
	}
	
	public static Comparator<ScrapingMarketIndex> byTitle = Comparator.comparing(ScrapingMarketIndex::getTitle);
	
	public String getTitle() {
		return this.marketIndex.getTitle();
	}
}	
