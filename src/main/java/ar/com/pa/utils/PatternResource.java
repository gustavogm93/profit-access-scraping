package ar.com.pa.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import ar.com.pa.enums.utils.ScrappingConstant;
import ar.com.pa.enums.utils.Summaries;

@Service
public class PatternResource {

	public static Pattern getPatternNumber() {
		return Pattern.compile("-?\\d+(\\.\\d+)?");
	}
	
	public static String getStandardUrl(String codeCompany, Summaries SummaryCode) {

		StringBuilder summaryUrl = new StringBuilder();
		
		summaryUrl.append(ScrappingConstant.fixUrl); 
		
		if(SummaryCode == Summaries.FS) 
			 summaryUrl.append(ScrappingConstant.urlFs.replace("#", codeCompany));
		else
			summaryUrl.append(ScrappingConstant.urlNotFs.replace("#", codeCompany).replace("$", SummaryCode.toString()));
			 
		return summaryUrl.toString();
	}
	
	public static boolean dateStringPattern(String s) {

			String literalMonthRegexp = "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s\\d{1,2},\\s(?i)\\d{4}";
			Pattern patter = Pattern.compile(literalMonthRegexp); 
			Matcher matcher = patter.matcher(s);
			return matcher.find();

	}
	
	
	
	
	
	
}

 