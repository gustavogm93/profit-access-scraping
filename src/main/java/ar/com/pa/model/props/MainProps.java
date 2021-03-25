package ar.com.pa.model.props;

import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;

@Data
public abstract class MainProps {
	
	@MongoId(targetType = FieldType.STRING)
	protected final String code;
	
	protected final String title;

	abstract static class Builder<T extends Builder<T>> {

		 private String code;
		 private String title;

		public T code(String code) {
			this.code = code;
			return self();
		}

		public T title(String title) {
			this.title = title;
			return self();
		}

		abstract MainProps build();

		protected abstract T self();
		
	}

	MainProps(Builder<?> builder) {
		code = builder.code;
		title = builder.title;
	}
}
