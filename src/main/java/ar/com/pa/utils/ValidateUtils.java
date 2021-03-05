package ar.com.pa.utils;

import java.util.Arrays;
import org.springframework.stereotype.Service;
import ar.com.pa.enums.financialsummary.FinancialSummary;

@Service
public class ValidateUtils {

    public ValidateUtils() {};
 

	public boolean isNumOrEmpty(String strNum) {
	    if (strNum.equalsIgnoreCase("")) {
	        return true;
	    }
	    try {
	        Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public boolean isSummaryModelValue(String s) {
	return isSummaryObject(s) || isNumOrEmpty(s);
	}
	
	
	public boolean isSummaryObject(String obj) {
		boolean response = Arrays.asList(FinancialSummary.values()).stream()
				.anyMatch(item -> item.getTitle().equalsIgnoreCase(obj));
		return response;
	}
}