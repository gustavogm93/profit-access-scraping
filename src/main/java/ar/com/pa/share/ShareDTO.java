package ar.com.pa.share;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Document(collection = "Share")
@Data
public class ShareDTO {

	@Id
	private String id;
	
	@Field(name = "properties")
	@NonNull private ShareProp properties;
	
}
