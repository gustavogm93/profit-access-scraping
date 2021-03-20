package ar.com.pa.model.company;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Document(collection = "Company")
@Component
public class CompanyDTO {

	@Id
	private String id; //WebCode
	
	private String title; //Index --> @Indexed

	private String Country; //Index --> @Indexed || Posible objeto
	
	private Market market; //Index  --> @Indexed
	
	private Industry industry; // Industry, Sector ---> @Indexed 

	private Flow fetchOperation;

	private FinancialSummary financialSummary;

	private Date lastUpdate;

	public CompanyDTO() {
	}

}
