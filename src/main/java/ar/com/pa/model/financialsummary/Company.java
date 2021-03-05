package ar.com.pa.model.financialsummary;

import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Document(collection="Company")
@Component
public class Company <T extends Summary> implements CompanyI<T>{

	String id;
	
	FinancialSummary financialSummaryYear;	
	FinancialSummary financialSummaryQuarter;
		
	BalanceSheet balanceSheetYear;	
	BalanceSheet balanceSheetQuarter;
		
	CashFlowStatement cashFlowStatementYear;
	CashFlowStatement cashFlowStatementQuarter;
	
	String state;
	
	Date lastUpdate;


	@Override
	public void setSummaryYear(T summaryYear) {

		if (summaryYear instanceof FinancialSummary obj) 
			this.financialSummaryYear = obj;
		
		else if (summaryYear instanceof BalanceSheet obj)
			this.balanceSheetYear = obj;	
		
		else if( summaryYear instanceof CashFlowStatement obj)
			this.cashFlowStatementYear = obj;		
		
	}

	
	@Override
	public void setSummaryQuarter(T summaryQuarter) {

		if (summaryQuarter instanceof FinancialSummary obj) 
			this.financialSummaryQuarter = obj;
		
		else if (summaryQuarter instanceof BalanceSheet obj)
			this.balanceSheetQuarter = obj;	
		
		else if( summaryQuarter instanceof CashFlowStatement obj)
			this.cashFlowStatementQuarter = obj;		
		
	}
	
	
/*
	  @SuppressWarnings("serial")
	  private final TypeToken<T> typeToken = new TypeToken<T>(getClass()) {};
	  private final Type type = typeToken.getType();
	  public Type getType() {return type;}
	*/
}
