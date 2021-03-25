package ar.com.pa.model.dto;

import java.util.List;
import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;

import ar.com.pa.model.props.CountryProps;
import ar.com.pa.model.props.MarketIndexProps;
import ar.com.pa.model.props.RegionProps;
import lombok.Data;

@Document(collection = "Country")
@Data
public class CountryDTO {

	
	private final CountryProps country;
	
	private final RegionProps region;
	
	private final List<MarketIndexProps> marketIndex;

	
	private CountryDTO(Builder builder) {
		country = builder.country;
		region = builder.region;
		marketIndex = builder.marketIndex;
	}

	public static class Builder {

		private CountryProps country;
		private RegionProps region;
		private List<MarketIndexProps> marketIndex;

		public Builder() {
		}

		public Builder region(RegionProps region) {
			this.region = region;
			return self();
		}

		public Builder marketIndex(List<MarketIndexProps> marketIndex) {
			this.marketIndex = marketIndex;
			return self();
		}

		public Builder props(CountryProps props) {
			this.country = props;
			return self();
		}

		public CountryDTO build() {
			return new CountryDTO(this);
		}

		protected Builder self() {
			return this;
		}

	}
}
