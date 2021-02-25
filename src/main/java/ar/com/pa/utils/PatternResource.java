package ar.com.pa.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import ar.com.pa.enums.utils.UrlPattern;
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
	
	public static boolean dateStringPattern(String s) {

			String literalMonthRegexp = "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s\\d{1,2},\\s(?i)\\d{4}";
			Pattern patter = Pattern.compile(literalMonthRegexp); 
			Matcher matcher = patter.matcher(s);
			return matcher.find();

	}
	
	
	
	
	
	
}

 