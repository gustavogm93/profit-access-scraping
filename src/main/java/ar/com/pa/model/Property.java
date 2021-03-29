package ar.com.pa.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class Property {


		@Field(name = "code")
		public String code;
		
		@Field(name = "title")
		public String title;
		
		public Property(String code, String title) {
		this.code = code;
		this.title = title;
	}
}
