package ar.com.pa.services;

import java.util.HashMap;

import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ar.com.pa.enums.FinancialSummary;
import ar.com.pa.enums.Periods;


import ar.com.pa.utils.ValidateUtils;

@Service
public class ScrappingFormat {
	
	final Logger logger = LoggerFactory.getLogger(ScrappingFormat.class);
	
	boolean isFinancialSummaryEnum(String text) {

		if(ValidateUtils.isNullOrEmpty(text)) return false;

		FinancialSummary[]financialEnums = FinancialSummary.values(); 

		for(FinancialSummary object :financialEnums) {
			if(object.getTitle() == text)
				return true;
		}
		return false;
	}
	

	/*

	*/
	
	public HashMap<String, String> reduceFinancialSummary(Elements elements) {
		  
		    logger.info("Scrapping financial Summary into HashMap and return it");
		try {
			String[] values = {"", "", ""};
			HashMap<String, String> financialSummaryMap = new HashMap<String, String>();

			for (int index = 0; index < elements.size(); index++) {

				if (ValidateUtils.isFinancialSummary(elements.get(index).ownText())) {
					values[0] = elements.get(index).ownText();

					for (int j = 1; j <= Periods.Quarter.getMonths(); j++) {
						values[2] = elements.get(index + j).ownText();
						values[1] += j + ":" + values[2] + " ";
					}
					
					index += Periods.Quarter.getMonths();
					financialSummaryMap.put(values[0], values[1]);
					values[0] = "";
					values[1] = "";
				}
			}
			return financialSummaryMap;
		}
		catch (Exception e) {
			logger.error("Error when Scrapping financial Summary " + e.getMessage());
			return null;
		}
	}
	/*
	public void finle(HashMap<String, String> hashMap) {
		
		IncomeStatementDTO incomeStatement = new IncomeStatementDTO(); 
		BalanceSheetDTO balanceSheet = new BalanceSheetDTO();
		CashFlowStatmentDTO cashFlowStatment = new CashFlowStatmentDTO();
		FinancialSummaryDTO financialSummary = new FinancialSummaryDTO();

		
		incomeStatement.setGrossProfit(OperatingIncome);
		
	    for (Map.Entry<String, String> entry : hashMap.entrySet()) {
	        String key = entry.getKey();
	        String value = entry.getValue();
	        System.out.println(key);
	        System.out.println(value);
	        
	       
		    if(key.equalsIgnoreCase(FinancialSummary.TotalRevenue.getByTitle())) {
		    	
		    }
		   

		    }
	    }*/
	   
	
}
