package ar.com.pa.model.financialsummary;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import ar.com.pa.model.FetchOperation;
import ar.com.pa.model.Industry;
import lombok.Data;

@Data
@Document(collection = "Company")
@Component
public class Company {

	@Id
	String id;
	
	String title;

	Industry profile;

	FetchOperation fetchOperation;

	FinancialSummary financialSummary;

	Date lastUpdate;

	public Company() {
	}

}
