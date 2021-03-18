package ar.com.pa.model.company;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Document(collection = "Company")
@Component
public class Company {

	@Id
	String id;
	
	String title;

	Market market;
	
	Industry industry;

	Flow fetchOperation;

	FinancialSummary financialSummary;

	Date lastUpdate;

	public Company() {
	}

}
