package ar.com.pa.enums.financialsummary;

import java.util.Arrays;

public enum FinancialSummary implements FinancialSummaryProperty<String> {

	TotalRevenue("Total revenue", FinancialSummaryStatement.incomeStatement),
	GrossProfit("Gross Profit", FinancialSummaryStatement.incomeStatement),
	OperatingIncome("Operating Income", FinancialSummaryStatement.incomeStatement),
	NetIncome("Net Income", FinancialSummaryStatement.incomeStatement),
	
	TotalAssets("Total Assets", FinancialSummaryStatement.balanceSheet),
	TotalLiabilities("Total Liabilities", FinancialSummaryStatement.balanceSheet),
	TotalEquity("Total Equity", FinancialSummaryStatement.balanceSheet),

	CashFromOperatingActivities("Cash From Operating Activities", FinancialSummaryStatement.cashFlowStatement),
	CashFromInvestingActivities("Cash From Investing Activities", FinancialSummaryStatement.cashFlowStatement),
	CashFromFinancingActivities("Cash From Financing Activities", FinancialSummaryStatement.cashFlowStatement),
	NetChangeInCash("Net Change in Cash", FinancialSummaryStatement.cashFlowStatement);


	public String title;
	public FinancialSummaryStatement statement;

	FinancialSummary(String title, FinancialSummaryStatement statement) {
		this.title = title;
		this.statement = statement;
	}
	FinancialSummary() {}
	
	@Override
	public String getTitle() {
		return title;
	}

	public FinancialSummaryStatement getStatement() {
		return statement;
	}

	public static FinancialSummary getFinancialSummaryByString(String obj) {
		FinancialSummary response = Arrays.asList(FinancialSummary.values()).stream()
				.filter(item -> item.getTitle().equalsIgnoreCase(obj)).findFirst().get();
		return response;
	}
	
	public static FinancialSummaryStatement getFinancialSummaryStatementByString(String obj) {
		FinancialSummary financialSummary = Arrays.asList(FinancialSummary.values()).stream()
				.filter(item -> item.getTitle().equalsIgnoreCase(obj)).findFirst().get();
		
		return financialSummary.getStatement();
	}
	

}
