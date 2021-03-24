package ar.com.pa.model;

import java.util.List;
import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Data;

@Document(collection = "CountryDTO")
@Data
public class CountryDTO extends State {

	private final Region region;
	private final List<MarketIndex> marketIndex ;
	
	
	
	public static class Builder extends State.Builder<Builder> {

		private Region region;
		private List<MarketIndex> marketIndex;
		
		public void region(Region region) {
			this.region = Objects.requireNonNull(region);
		}
		
		public void marketIndex(List<MarketIndex> marketIndex) {
			this.marketIndex = Objects.requireNonNull(marketIndex);
		}
		
		@Override
		public CountryDTO build() {
			return new CountryDTO(this);
		}

		@Override
		protected Builder self() {
			return this;
		}

	}

	private CountryDTO(Builder builder) {
		super(builder);
		region = builder.region;
		marketIndex = builder.marketIndex;
	}

		
}
