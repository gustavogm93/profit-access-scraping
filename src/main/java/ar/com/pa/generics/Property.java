package ar.com.pa.generics;

import java.io.Serializable;
import java.util.Comparator;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Property implements Serializable {

		@Field(name = "code")
		@NonNull private final String code;
		
		@Field(name = "title")
		@NonNull private final String title;

		public static Comparator<Property> byTitle = Comparator.comparing(Property::getTitle);
		public static Comparator<Property> byCode = Comparator.comparing(Property::getCode);
	
}
