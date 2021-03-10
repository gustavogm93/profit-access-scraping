package ar.com.pa.utils;

import java.util.Arrays;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ar.com.pa.enums.BalanceSheet;
import ar.com.pa.enums.financialsummary.FinancialSummary;
import ar.com.pa.enums.utils.SummaryType;

public class ValidateUtilsImpl implements ValidateUtils {

    public ValidateUtilsImpl() {};
 

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
	
	
	public boolean isSummaryModelValue(String s, SummaryType summaryPerYear) {
	return isSummaryObject(s, summaryPerYear) || isNumOrEmpty(s);
	}
	
	//DEBE FILTRAR POR TYPE SUMMARY
	public  boolean isSummaryObject(String obj,SummaryType summaryPerYear) {
		boolean response = false;
		
		switch(summaryPerYear) {
		
		case FS -> { response = Arrays.asList(FinancialSummary.values()).stream()
			.anyMatch(item -> item.getTitle().equalsIgnoreCase(obj)); }
		
		case BAL -> {
			response = Arrays.asList(BalanceSheet.values()).stream()
					.anyMatch(item -> item.getTitle().equalsIgnoreCase(obj)); 
		}
		
		}
		
		return response;
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