package ar.com.pa.model;

import java.io.Serializable;
import java.util.Comparator;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class Property implements Serializable {


		@Field(name = "code")
		public String code;
		
		@Field(name = "title")
		public String title;
		
		public Property(String code, String title) {
		this.code = code;
		this.title = title;
	}


		public static Comparator<Property> byTitle = Comparator.comparing(Property::getTitle);
		public static Comparator<Property> byCode = Comparator.comparing(Property::getCode);

		
}
