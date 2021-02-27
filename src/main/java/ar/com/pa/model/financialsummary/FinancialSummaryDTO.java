package ar.com.pa.model.financialsummary;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import ar.com.pa.enums.financialsummary.FinancialSummary;
import ar.com.pa.enums.financialsummary.FinancialSummaryStatement;
import lombok.Data;

@Data
@Entity
@Table(name="FINANCIAL_SUMMARY")
public class FinancialSummaryDTO {
	
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	
	@Column(name="STATEMENT")
	private FinancialSummaryStatement statement;
	
	@Column(name="SUMMARY")
	private FinancialSummary summary;
	
	@Column(name="PERIOD_ENDING")
	private Date periodEnding;
	
	@Column(name="VALUE")
	private int value;
	
	
	public FinancialSummaryDTO() {}
	
	public FinancialSummaryDTO(FinancialSummary summary, Date periodEnding, int value) {
		this.statement = summary.getStatement();
		this.summary = summary;
		this.periodEnding = periodEnding;
		this.value = value;
	}

	
}
