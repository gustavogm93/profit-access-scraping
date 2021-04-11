package ar.com.pa.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.function.Supplier;

import org.springframework.data.mongodb.core.mapping.Field;

import ar.com.pa.model.props.Country;
import lombok.Data;
import static com.google.common.base.Preconditions.checkNotNull;

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
		public static Supplier<TreeSet<Country>> newTreeSet= () -> new TreeSet<Country>(byCode);
}
