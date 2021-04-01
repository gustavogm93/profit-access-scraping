package ar.com.pa.model.props;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
public abstract class MainProps {
	
	public final String code;
	
	public final String title;

	public abstract static class Builder<T extends Builder<T>> {

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

	protected MainProps(Builder<?> builder) {
		code = builder.code;
		title = builder.title;
	}
}