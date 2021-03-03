package ar.com.pa.model.financialsummary;

import java.util.List;

import org.springframework.data.annotation.Id;
import lombok.Data;

@Data
public class FinancialSummaryDTO {
	
	
	@Id 
	private String id;
		

	private List<FinancialDTO> incomeStatement;
	private List<FinancialDTO> balanceSheet;
	private List<FinancialDTO> cashFlowStatement;
	

	public FinancialSummaryDTO() {}

    public String getId() {
        return id;
    }
}
