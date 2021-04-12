package ar.com.pa.company;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Document(collection = "Company")
@Component
public class CompanyDTO {

	@Id
	private String id; 
	
	@Field(name = "title")
	private String title; 

	@Field(name = "Country")
	private String Country; 
	
	@Field(name = "market")
	private Market market;
	
	@Field(name = "industry")
	private Industry industry;

	@Field(name = "fetchOperation")
	private Flow fetchOperation;

	@Field(name = "financialSummary")
	private FinancialSummary financialSummary;

	@Field(name = "lastUpdate")
	private Date lastUpdate;

	public CompanyDTO() {
	}

}
