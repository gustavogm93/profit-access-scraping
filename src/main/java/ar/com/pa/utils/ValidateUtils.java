package ar.com.pa.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ar.com.pa.enums.FinancialSummary;
import ar.com.pa.enums.FinancialSummaryConverter;
import ar.com.pa.enums.Periods;
import ar.com.pa.model.financial_summary.FinancialSummaryDTO;
import ar.com.pa.repository.FinancialSummaryRepository;


@Service
public class ValidateUtils {
	
	

    private FinancialSummaryRepository financialSummaryRepository;
    
    @Autowired
    private FinancialSummaryConverter financialSummaryConverter;
    
    public ValidateUtils() {};
    @Autowired
    public ValidateUtils(FinancialSummaryRepository financialSummaryRepository) {
    	this.financialSummaryRepository = financialSummaryRepository;
    }
    
	public static boolean isNullOrEmpty(String str) {

		if (str != null && !str.isEmpty())
			return false;
		return true;
	}

	public static boolean isNumericOrNull(String strNum) {
		if (strNum == null || strNum.equalsIgnoreCase("")) {
			return true;
		}
		Pattern patternResource = PatternResource.getPatternNumber();
		return patternResource.matcher(strNum).matches();
	}

	public static boolean isFinancialSummary(String str) {
		boolean response = Arrays.asList(FinancialSummary.values()).stream()
				.anyMatch(item -> item.getTitle().equalsIgnoreCase(str));

		return response;

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
	        	FinancialSummary summary = financialSummaryConverter.convertToEntityAttribute(key);	
	        	
	        	financialSummaryToAdd.setCompanyId(10);
	        	financialSummaryToAdd.setId(110);	        	  	    	
	        	financialSummaryToAdd.setStatement(summary.getStatement());
	 	        financialSummaryToAdd.setSummary(summary);
	        	financialSummaryToAdd.setValue(MapperElement.convertToValue(items, i));
	        	financialSummaryToAdd.setPeriodEnding(MapperElement.convertToDate(items, periods, i));
	        	
	        	ListfinancialSummary.add(financialSummaryToAdd);
		    }
	       
	    }
	    List<FinancialSummaryDTO> ListfinancialSummary2 = new ArrayList <FinancialSummaryDTO>();

	    this.financialSummaryRepository.save(ListfinancialSummary.get(1));
	    
	    try {
	    	long a = 6;
	    	FinancialSummaryDTO ListfinancialSummary3 = this.financialSummaryRepository.findById(a);
	    System.out.println(ListfinancialSummary3.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	  

	    
	    
		
	}

	public FinancialSummaryRepository getFinancialSummaryRepository() {
		return financialSummaryRepository;
	}

	public void setFinancialSummaryRepository(FinancialSummaryRepository financialSummaryRepository) {
		this.financialSummaryRepository = financialSummaryRepository;
	}

}