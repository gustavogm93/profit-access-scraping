package ar.com.pa.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.jsoup.select.Elements;
import org.springframework.data.mongodb.core.mapping.Document;

import ar.com.pa.enums.utils.SummaryType;
import lombok.Data;

@Data
@Document(collection="FetchOperation")
public class FetchOperation {
	
String title;
String code;
Date last_update;

List<String> intervalTime;

HashMap<SummaryType, Integer> attempts;
HashMap<SummaryType, String> urlList;
//HashMap<SummaryType, Elements> elementListFailed;

boolean tempError = false;

public FetchOperation() {}

}
