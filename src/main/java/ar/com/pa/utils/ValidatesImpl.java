package ar.com.pa.utils;

import ar.com.pa.enums.financialsummary.FinancialSummary;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ValidatesImpl implements Validates {

    public ValidatesImpl() {};


	public void stringIsNumeric(String id) throws NumberFormatException {
			Integer.parseInt(id);
	}
	
	public boolean isNumOrEmpty(String strNum) {
	    if (strNum.equalsIgnoreCase("") || strNum.equalsIgnoreCase("-")) {
	        return true;
	    }
	    try {
	        Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public boolean isString(String str) {
		 if (str.equalsIgnoreCase("")) {
		        return false;
		    }
		 
		 if( str.charAt(0) >= 48 && str.charAt(0) <= 57 )
			 return false;
		 
		 if(str.equalsIgnoreCase("Period Length:"))
			 return false;
		 
		try {
	        Double.parseDouble(str);
	    } catch (NumberFormatException nfe) {
	        return true;
	    }
		return false;
	}
	
	
	public boolean isSummaryModelValue(String string) {
	return  isNumOrEmpty(string) || isSummaryObject(string) ;
	}
	
	
	public boolean isSummaryObject(String obj) {

		return Arrays.asList(FinancialSummary.values()).stream()
				.anyMatch(item -> item.getTitle().equalsIgnoreCase(obj));

	}
	
	public boolean isDate(String s) {

		try {
			Double.parseDouble(s.substring(0, 1));
		} catch (NumberFormatException nfe) {
			return false;
		}

		return true;
	}

	
	
}