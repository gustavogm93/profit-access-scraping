package ar.com.pa.model.company;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

import ar.com.pa.enums.utils.SummaryType;
import lombok.Data;

@Data
@Document(collection="Flow")
public class Flow {
	
String title;
String code;
Date last_update;

List<String> intervalTime;

HashMap<SummaryType, Integer> attempts;
HashMap<SummaryType, String> urlList;

boolean tempError = false;

public Flow() {}

}
