package ar.com.pa.enums.financialsummary;

import java.util.Arrays;

public enum FinancialSummaryStatement implements FinancialSummaryProperty<String>{
	
	incomeStatement("income statement"), balanceSheet("balance sheet"), cashFlowStatement("cash flow statement");

	String title;

	FinancialSummaryStatement(String title) {this.title = title;}
	
	@Override
	public String getTitle() {return this.title;}
 
	public static FinancialSummaryStatement getFinancialSummaryByString(String obj) {
		FinancialSummaryStatement response = Arrays.asList(FinancialSummaryStatement.values()).stream()
				.filter(item -> item.getTitle().equalsIgnoreCase(obj)).findFirst().get();
		return response;
	}
}
