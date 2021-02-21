package ar.com.pa.utils;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import ar.com.pa.enums.UrlPattern;
@Service
public class PatternResource {

	public static Pattern getPatternNumber() {
		return Pattern.compile("-?\\d+(\\.\\d+)?");
	}
	
	public static String getFinancialSummaryUrl(String codeCompany, UrlPattern period) {
		String url = UrlPattern.UrlPeriod.getPattern().replace("#", codeCompany)
													  .concat(period.getPattern());
		
		return url;
	}
	
	
	
	
	
	
	
	
}
