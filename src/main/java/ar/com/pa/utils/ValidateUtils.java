package ar.com.pa.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.pa.enums.Periods;
import ar.com.pa.enums.financialsummary.FinancialSummary;
import ar.com.pa.model.financialsummary.FinancialSummaryDTO;
import ar.com.pa.repository.FinancialSummaryRepository;


@Service
public class ValidateUtils {

	@Autowired
    private FinancialSummaryRepository financialSummaryRepository;

    public ValidateUtils() {};
 
	public static boolean isNullOrEmpty(String str) {

		if (str != null && !str.isEmpty())
			return false;
		return true;
	}

	
	public void bacMapper(HashMap<String, String> scrappedElements) {
		
		List<FinancialSummaryDTO> ListfinancialSummary = new ArrayList <FinancialSummaryDTO>();
		String[] periods = {"Dec 26, 2020", "Sep 26, 2020","Jun 27, 2020","Mar 28, 2020"}; 

	    for (Map.Entry<String, String> entry : scrappedElements.entrySet()) {
	    	
	        String key = entry.getKey();
	        String value = entry.getValue();
	        

	        String[] items = value.split(" ");
	        
	        for(int i=0; i<Periods.Quarter.getMonths(); i++) {
	        	
	        	FinancialSummaryDTO financialSummaryToAdd = new FinancialSummaryDTO();
	        	FinancialSummary summary = FinancialSummary.getFinancialSummaryByString(key);

	        	financialSummaryToAdd.setCompanyId(10);        	  	    	
	        	financialSummaryToAdd.setStatement(summary.getStatement());
	 	        financialSummaryToAdd.setSummary(summary);
	        	financialSummaryToAdd.setValue(MapperElement.convertToValue(items, i));
	        	financialSummaryToAdd.setPeriodEnding(MapperElement.convertToDate(items, periods, i));
	        	
	        	ListfinancialSummary.add(financialSummaryToAdd);
		    }
	       
	    }
	    List<FinancialSummaryDTO> ListfinancialSummary2 = new ArrayList <FinancialSummaryDTO>();

	   // this.financialSummaryRepository.save(ListfinancialSummary.get(1));
	    
	    try {
	    	long a = 21;
	    	FinancialSummaryDTO ListfinancialSummary3 = this.financialSummaryRepository.findById(a);
	    System.out.println(ListfinancialSummary3.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
	}


}