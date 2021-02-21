package ar.com.pa.enums;


public enum FinancialSummaryStatement {
	 incomeStatement("income statement"),
	 balanceSheet("balance sheet"),
	 cashFlowStatement("cash flow statement");
	
	String title;
	
	private FinancialSummaryStatement(String title) {this.title = title;}
}
