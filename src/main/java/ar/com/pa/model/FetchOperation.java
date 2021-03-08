package ar.com.pa.model;

import java.util.Date;
import java.util.HashMap;

import org.springframework.data.mongodb.core.mapping.Document;

import ar.com.pa.enums.utils.SummaryType;
import lombok.Data;

@Data
@Document(collection="CompanyOperation")
public class FetchOperation {

String state;	
String title;
String code;
Date last_update;
Integer attempts;
HashMap<SummaryType, String> urlList;

public FetchOperation() {}

}
