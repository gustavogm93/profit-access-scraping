package ar.com.pa.model.financial_summary;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Index;
import ar.com.pa.enums.FinancialSummary;
import ar.com.pa.enums.FinancialSummaryStatement;
import lombok.Data;

@Data
@Entity
@Table(name="FINANCIAL_SUMMARY")
public class FinancialSummaryDTO {
	
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	
	@Column(name="STATEMENT")
	@Enumerated(EnumType.STRING)
	private FinancialSummaryStatement statement;
	
	@Column(name="SUMMARY")
	private FinancialSummary summary;
	
	@Column(name="PERIOD_ENDING")
	private Date periodEnding;
	
	@Column(name="VALUE")
	private int value;
	
	@Column(name="COMPANY_ID")
	private int companyId;
	
	public FinancialSummaryDTO() {}
	
	public FinancialSummaryDTO(int companyId, FinancialSummaryStatement statement, FinancialSummary summary) {
		this.companyId = companyId;
		this.statement = statement;
		this.summary = summary;
	}
}
