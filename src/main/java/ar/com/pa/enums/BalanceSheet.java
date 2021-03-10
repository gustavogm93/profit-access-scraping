package ar.com.pa.enums;

import java.util.Arrays;

import ar.com.pa.enums.financialsummary.FinancialSummary;

public enum BalanceSheet {

	TotalAssets("Total Assets"),
	CashAndDuefromBanks("Cash & Due from Banks"),
	OtherEarningAssetsTotal("Other Earning Assets, Total"),
	NetLoans("Net Loans"),
	PropertyAndPlantAndEquipmentTotalNet("Property/Plant/Equipment, Total - Net"),
	PropertyAndPlantAndEquipmentTotalGross("Property/Plant/Equipment, Total - Gross"),
	AccumulatedDepreciationTotal("Accumulated Depreciation, Total"),
	GoodwillNet("Goodwill, Net"),
	IntangiblesNet("Intangibles, Net"),
	LongTermInvestments("Long Term Investments"),
	OtherLongTermAssetsTotal("Other Long Term Assets, Total"),
	OtherAssetsTotal("Other Assets, Total"),
	TotalCurrentLiabilities("Total Current Liabilities"),
	TotalLiabilities("Total Liabilities"),
	AccountsPayable("Accounts Payable"),
	PayableAndAccrued("Payable/Accrued"),
	AccruedExpenses("Accrued Expenses"),
	TotalDeposits("Total Deposits"),
	OtherBearingLiabilitiesTotal("Other Bearing Liabilities, Total"),
	TotalShortTermBorrowings("Total Short Term Borrowings"),
	CurrentPortofLTDebtAndCapitalLeases("Current Port. of LT Debt/Capital Leases"),
	OtherCurrentliabilitiesTotal("Other Current liabilities, Total"),
	TotalLongTermDebt("Total Long Term Debt"),
	LongTermDebt("Long Term Debt"),
	CapitalLeaseObligations("Capital Lease Obligations"),
	DeferredIncomeTax("Deferred Income Tax"),
	MinorityInterest("Minority Interest"),
	OtherLiabilitiesTotal("Other Liabilities, Total"),
	TotalEquity("Total Equity"),
	RedeemablePreferredStockTotal("Redeemable Preferred Stock, Total"),
	PreferredStockNonRedeemableNet("Preferred Stock - Non Redeemable, Net"),
	CommonStockTotal("Common Stock, Total"),
	AdditionalPaidInCapital("Additional Paid-In Capital"),
	RetainedEarnings("Retained Earnings (Accumulated Deficit)"),
	TreasuryStockCommon("Treasury Stock - Common"),
	ESOPDebtGuarantee("ESOP Debt Guarantee"),
	UnrealizedGain("Unrealized Gain (Loss)"),
	OtherEquityTotal("Other Equity, Total"),
	TotalLiabilitiesAndShareholdersEquity("Total Liabilities & Shareholders' Equity"),
	TotalCommonSharesOutstanding("Total Common Shares Outstanding"),
	TotalPreferredSharesOutstanding("Total Preferred Shares Outstanding");

	public String title;
	
	BalanceSheet(String title) {
		this.title = title;
	}
	BalanceSheet() {}
	
	public String getTitle() {
		return title;
	}

	public static BalanceSheet getbalanceSheetByString(String obj) {
		BalanceSheet response = Arrays.asList(BalanceSheet.values()).stream()
				.filter(item -> item.getTitle().equalsIgnoreCase(obj)).findFirst().get();
		return response;
	}
}
