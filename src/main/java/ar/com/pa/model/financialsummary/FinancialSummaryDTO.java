package ar.com.pa.model.financialsummary;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.pa.enums.financialsummary.FinancialSummary;
import ar.com.pa.enums.financialsummary.FinancialSummaryStatement;
import ar.com.pa.mapper.FinancialSummaryConverter;
import ar.com.pa.mapper.FinancialSummaryStatementConverter;
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
	
	@Column(name="COMPANY_ID")
	private int companyId;
	

	
	public FinancialSummaryDTO() {}
	
	public FinancialSummaryDTO(int companyId, FinancialSummaryStatement statement, FinancialSummary summary) {
		this.companyId = companyId;
		this.statement = statement;
		this.summary = summary;
	}
}
