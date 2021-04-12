package ar.com.pa.summary;

import ar.com.pa.marketIndex.MarketIndexProp;
import java.util.Comparator;
import lombok.Data;

@Data
public class SummaryMarketIndexData {

	MarketIndexProp marketIndex;
	Integer sharesQuantity;
	
	public SummaryMarketIndexData(MarketIndexProp marketIndex, Integer sharesQuantity) {
		this.marketIndex = marketIndex;
		this.sharesQuantity = sharesQuantity;
	}
	
	public static Comparator<SummaryMarketIndexData> byTitle = Comparator.comparing(SummaryMarketIndexData::getTitle);
	
	public String getTitle() {
		return this.marketIndex.getTitle();
	}
}	
