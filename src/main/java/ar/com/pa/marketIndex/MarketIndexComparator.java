package ar.com.pa.marketIndex;

import java.util.Comparator;

public class MarketIndexComparator {

	public static Comparator<MarketIndexDTO> byTitle = Comparator.comparing(MarketIndexDTO::getTitle);
	public static Comparator<MarketIndexDTO> byCode = Comparator.comparing(MarketIndexDTO::getTitle);
}
