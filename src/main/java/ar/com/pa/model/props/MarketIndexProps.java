package ar.com.pa.model.props;

public class MarketIndexProps extends MainProps {

	public static class Builder extends MainProps.Builder<Builder> {

		@Override
		public MarketIndexProps build() {
			return new MarketIndexProps(this);
		}

		@Override
		protected Builder self() {
			return this;
		}

	}

	protected MarketIndexProps(Builder builder) {
		super(builder);
	}

}
