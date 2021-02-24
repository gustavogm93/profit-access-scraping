package ar.com.pa.utils;

import java.util.regex.Matcher;
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
	
	public static void dateStringPattern() {
		
		String monthPattern = "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)";
		
		String regexPattern = "^(([0-9])|([0-2][0-9])|( ([3][0-1])\\B))";
		Pattern patter = Pattern.compile(regexPattern); 
		Matcher matcher = patter.matcher("353");
		boolean boo = matcher.find();
		System.out.println(boo);
		
	}
	
	
	
	
	
	
}

 