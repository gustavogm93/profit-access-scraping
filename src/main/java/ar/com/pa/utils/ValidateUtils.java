package ar.com.pa.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ar.com.pa.enums.financialsummary.FinancialSummary;
import ar.com.pa.model.financialsummary.FinancialSummaryDTO;
import ar.com.pa.repository.FinancialSummaryRepository;


@Service
public class ValidateUtils {

    public ValidateUtils() {};
 

	public static boolean isNumOrEmpty(String strNum) {
	    if (strNum.equalsIgnoreCase("")) {
	        return true;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public static boolean isSummaryValue(String s) {
	return FinancialSummary.isSummary(s) || ValidateUtils.isNumOrEmpty(s);
	}
	
	
}