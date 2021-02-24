package ar.com.pa.enums.financialsummary;

public enum FinancialSummaryStatement implements FinancialSummaryProperty<String>{
	
	incomeStatement("income statement"), balanceSheet("balance sheet"), cashFlowStatement("cash flow statement");

	String title;

	FinancialSummaryStatement(String title) {this.title = title;}
	
	public String getTitle() {return title;}
 

}
