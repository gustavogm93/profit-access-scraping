package ar.com.pa.model.financialsummary;

import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import ar.com.pa.model.FetchOperation;
import lombok.Data;

@Data
@Document(collection="Company")
@Component
public class Company{

	String id;
	String name;
	FetchOperation fetchOperation;
	
	FinancialSummaryDTO financialSummary;	
	
	BalanceSheetDTO balanceSheet;	
		
	CashFlowStatementDTO cashFlowStatement;

	IncomeStatementDTO incomeStatement; 
	
	Date lastUpdate;

	public Company() {}

}
