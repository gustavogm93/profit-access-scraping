package ar.com.pa.collections.coverage;

import java.util.List;

import com.google.common.base.Predicate;
import ar.com.pa.collections.summary.ScrapedCoverageData;

public class VerifyCoverage {
/*
	public static void filterDataWitouthCoverage(ScrapedCoverageData coverageList) {
		
		coverageList.stream().filter(withoutValidCoverage)
	}
	*/
	public static Predicate<ScrapedCoverageData> withoutValidCoverage = (element) -> withoutValidCoverage(element);

	
	public static boolean withoutValidCoverage(ScrapedCoverageData coverageItem) {

		int timesScraped = coverageItem.getTimesScraping();
		int coverage = coverageItem.getCoverage();

		if (wasScrapedLessThanTwoTimes(timesScraped))
			return true;

		if (coverage < 89)
			return true;

		return false;
	}

	public static boolean wasScrapedLessThanTwoTimes(int timesScraped) {
		return timesScraped < 2;
	}

}
