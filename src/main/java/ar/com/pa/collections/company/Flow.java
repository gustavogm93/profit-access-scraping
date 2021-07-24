package ar.com.pa.collections.company;

import ar.com.pa.enums.utils.SummaryType;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
