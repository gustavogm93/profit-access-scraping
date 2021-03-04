package ar.com.pa.model.financialsummary;

import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection="Company")
public class Company <T extends Summary> implements CompanyI<T>{

	String id;
	
	FinancialSummary financialSummaryYear;	
	FinancialSummary financialSummaryQuarter;
		
	BalanceSheet balanceSheetYear;	
	BalanceSheet balanceSheetQuarter;
		
	cashFlowStatement cashFlowStatementYear;
	cashFlowStatement cashFlowStatementQuarter;
	
	String state;
	
	Date lastUpdate;


	@Override
	public void setSummaryYear(T summaryYear) {

		if (summaryYear instanceof FinancialSummary obj) 
			this.financialSummaryYear = obj;
		
		else if (summaryYear instanceof BalanceSheet obj)
			this.balanceSheetYear = obj;	
		
		else if( summaryYear instanceof cashFlowStatement obj)
			this.cashFlowStatementYear = obj;		
		
	}

	
	@Override
	public void setSummaryQuarter(T summaryQuarter) {

		if (summaryQuarter instanceof FinancialSummary obj) 
			this.financialSummaryQuarter = obj;
		
		else if (summaryQuarter instanceof BalanceSheet obj)
			this.balanceSheetQuarter = obj;	
		
		else if( summaryQuarter instanceof cashFlowStatement obj)
			this.cashFlowStatementQuarter = obj;		
		
	}
	
	
/*
	  @SuppressWarnings("serial")
	  private final TypeToken<T> typeToken = new TypeToken<T>(getClass()) {};
	  private final Type type = typeToken.getType();
	  public Type getType() {return type;}
	*/
}
