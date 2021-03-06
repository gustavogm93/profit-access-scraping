package ar.com.pa.model.financialsummary;

import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Document(collection="Company")
@Component
public class Company{

	String id;
	String state;
	String code;
	String title;
	
	FinancialSummary financialSummary;	
	
	BalanceSheet balanceSheet;	
		
	CashFlowStatement cashFlowStatement;

	Date lastUpdate;

	
}
