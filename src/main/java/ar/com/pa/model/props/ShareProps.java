package ar.com.pa.model.props;

public class ShareProps extends MainProps {

	public static class Builder extends MainProps.Builder<Builder> {

		@Override
		public ShareProps build() {
			return new ShareProps(this);
		}

		@Override
		protected Builder self() {
			return this;
		}

	}

	protected ShareProps(Builder builder) {
		super(builder);
	}
}
