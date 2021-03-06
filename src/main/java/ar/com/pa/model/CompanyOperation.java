package ar.com.pa.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Document(collection="CompanyConstants")
@Component
public class CompanyOperation {

String state;	
String title;
String companyCode;


public CompanyOperation(String companyCode) {
	this.companyCode = companyCode;
}

}
