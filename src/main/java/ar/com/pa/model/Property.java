package ar.com.pa.model;

import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Data;

@Data
public class Property {

		@Indexed
		public String code;
		
		public String title;
		
		public Property(String code, String title) {
		this.code = code;
		this.title = title;
	}
}
