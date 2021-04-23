package ar.com.pa.collections.share;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class ShareDTO {

	@Id
	private String id;
	
	@Field(name = "properties")
	@NonNull private ShareProp properties;
	
}
