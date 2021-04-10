package ar.com.pa.model.constant;

import java.util.Comparator;

import ar.com.pa.model.props.MarketIndex;
import lombok.Data;

@Data
public class SummaryMarketIndexData {

	MarketIndex marketIndex;
	Integer sharesQuantity;
	
	public SummaryMarketIndexData(MarketIndex marketIndex, Integer sharesQuantity) {
		this.marketIndex = marketIndex;
		this.sharesQuantity = sharesQuantity;
	}
	
	public static Comparator<SummaryMarketIndexData> byTitle = Comparator.comparing(SummaryMarketIndexData::getTitle);
	
	public String getTitle() {
		return this.marketIndex.getTitle();
	}
}	
