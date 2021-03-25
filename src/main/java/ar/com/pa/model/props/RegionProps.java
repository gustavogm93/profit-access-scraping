package ar.com.pa.model.props;

public class RegionProps extends MainProps {

	public static class Builder extends MainProps.Builder<Builder> {

		@Override
		public RegionProps build() {
			return new RegionProps(this);
		}

		@Override
		protected Builder self() {
			return this;
		}

	}

	protected RegionProps(Builder builder) {
		super(builder);
	}

}
