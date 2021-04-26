package ar.com.pa.collections.summary;

import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;
import lombok.NonNull;

@Data
public class ScrapedData {

	@Field(name = "code")
	@NonNull private String code;
	
	@Field(name = "title")
	@NonNull private String title;

	public ScrapedData(@NonNull String code, @NonNull String title) {
		this.code = code;
		this.title = title;
	}
	
}
