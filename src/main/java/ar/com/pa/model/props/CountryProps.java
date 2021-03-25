package ar.com.pa.model.props;

public class CountryProps extends MainProps {

	public static class Builder extends MainProps.Builder<Builder> {

		
		public Builder() {}
		
		@Override
		public CountryProps build() {
			return new CountryProps(this);
		}

		@Override
		protected Builder self() {
			return this;
		}

	}

	protected CountryProps(Builder builder) {
		super(builder);
	}

}
