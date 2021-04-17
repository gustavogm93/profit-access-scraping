package ar.com.pa.generics;

import java.io.Serializable;
import java.util.Comparator;
import org.springframework.data.mongodb.core.mapping.Field;
import static com.google.common.base.Preconditions.checkNotNull;
import lombok.Data;

@Data
public class Property implements Serializable {

		@Field(name = "code")
		private final String code;
		
		@Field(name = "title")
		private final String title;
		
		public Property(String code, String title) {
		this.code = checkNotNull(code);
		this.title = checkNotNull(title);
	}

		public static Comparator<Property> byTitle = Comparator.comparing(Property::getTitle);
		public static Comparator<Property> byCode = Comparator.comparing(Property::getCode);
	
}
