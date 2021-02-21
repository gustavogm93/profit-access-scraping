package ar.com.pa.enums;
/**
 * A list of {@link FinancialSummary} attributes used to mapping to
 * Company-hub-admin.financialSummary SQL
 */
public enum FinancialSummary {

	TotalRevenue("Total revenue", FinancialSummaryStatement.incomeStatement),
	GrossProfit("Gross Profit", FinancialSummaryStatement.incomeStatement),
	OperatingIncome("Operating Income", FinancialSummaryStatement.incomeStatement),
	NetIncome("Net Income", FinancialSummaryStatement.incomeStatement),
	
	GrossMargin("Gross margin", FinancialSummaryStatement.incomeStatement),
	OperatingMargin("Operating margin", FinancialSummaryStatement.incomeStatement),
	NetProfitMargin("Net Profit margin", FinancialSummaryStatement.incomeStatement),
	ReturnOnInvestment("Return on Investment", FinancialSummaryStatement.incomeStatement),
	
	
	TotalAssets("Total Assets", FinancialSummaryStatement.balanceSheet),
	TotalLiabilities("Total Liabilities", FinancialSummaryStatement.balanceSheet),
	TotalEquity("Total Equity", FinancialSummaryStatement.balanceSheet),
	
	QuickRatio("Quick Ratio", FinancialSummaryStatement.balanceSheet),
	CurrentRatio("Current Ratio", FinancialSummaryStatement.balanceSheet),
	LTDebttoEquity("LT Debt to Equity", FinancialSummaryStatement.balanceSheet),
	TotalDebttoEquity("Total Debt to Equity", FinancialSummaryStatement.balanceSheet),
	
	
	CashFromOperatingActivities("Cash From Operating Activities",FinancialSummaryStatement.cashFlowStatement),
	CashFromInvestingActivities("Cash From Investing Activities",FinancialSummaryStatement.cashFlowStatement),
	CashFromFinancingActivities("Cash From Financing Activities",FinancialSummaryStatement.cashFlowStatement),
	NetChangeInCash("Net Change in Cash",FinancialSummaryStatement.cashFlowStatement),
	
	CashFlowShare("Cash Flow/Share",FinancialSummaryStatement.cashFlowStatement),
	RevenueShare("Revenue/Share",FinancialSummaryStatement.cashFlowStatement),	
	OperatingCashFlow("Operating Cash Flow",FinancialSummaryStatement.cashFlowStatement);
	

	private String title;
	private FinancialSummaryStatement statement;
	FinancialSummary(String title, FinancialSummaryStatement statement) {
		this.title = title;
		this.statement = statement;
	}

	public String getTitle() {
		return title;
	}

	public FinancialSummaryStatement getStatement() {return statement;}

}
