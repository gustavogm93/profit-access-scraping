package ar.com.pa.enums.financialsummary;

import java.util.Arrays;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public enum FinancialSummaryStatement implements FinancialSummaryProperty<String>{
	
	incomeStatement("income statement"), balanceSheet("balance sheet"), cashFlowStatement("cash flow statement");

	String title;

	FinancialSummaryStatement(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	@Override
	public boolean isSummary(String obj) {
		boolean response = Arrays.asList(FinancialSummary.values()).stream()
				.anyMatch(item -> item.getTitle().equalsIgnoreCase(obj));

		return response;
	}

}
