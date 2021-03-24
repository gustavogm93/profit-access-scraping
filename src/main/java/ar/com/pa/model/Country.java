package ar.com.pa.model;

public class Country extends State {

	private final int flag;

	public static class Builder extends State.Builder<Builder> {

		private final int flag;

		public Builder(int flag) {

			this.flag = flag;
		}

		@Override
		public Country build() {
			return new Country(this);
		}

		@Override
		protected Builder self() {
			return this;
		}

	}

	private Country(Builder builder) {
		super(builder);
		flag = builder.flag;
	}

}
