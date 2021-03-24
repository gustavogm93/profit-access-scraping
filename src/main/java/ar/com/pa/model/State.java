package ar.com.pa.model;

import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;

@Data
public abstract class State {
	
	@MongoId(targetType = FieldType.STRING)
	final String code;
	final String title;

	abstract static class Builder<T extends Builder<T>> {

		 String code;
		 String title;

		public T code(String code) {
			this.code = code;
			return self();
		}

		public T title(String title) {
			this.title = title;
			return self();
		}

		abstract State build();

		protected abstract T self();
	}

	State(Builder<?> builder) {
		code = builder.code;
		title = builder.title;
	}
}
