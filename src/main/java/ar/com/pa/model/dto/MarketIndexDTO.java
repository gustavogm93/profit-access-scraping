package ar.com.pa.model.dto;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import ar.com.pa.model.props.CountryProps;
import ar.com.pa.model.props.MarketIndexProps;
import ar.com.pa.model.props.ShareProps;
import lombok.Data;

@Document(collection = "MarketIndex")
@Component
@Data
public class MarketIndexDTO {

	private final MarketIndexProps marketIndex;

	private final CountryProps country;

	private final List<ShareProps> shares;

	
	private MarketIndexDTO(Builder builder) {
		marketIndex = builder.marketIndex;
		country = builder.country;
		shares = builder.shares;
	}

	public static class Builder {

		private MarketIndexProps marketIndex;

		private CountryProps country;

		private List<ShareProps> shares;

		public Builder() {
		}

		public Builder marketIndex(MarketIndexProps marketIndex) {
			this.marketIndex = marketIndex;
			return self();
		}

		public Builder props(CountryProps countryProps) {
			this.country = countryProps;
			return self();
		}

		public Builder shares(List<ShareProps> shares) {
			this.shares = shares;
			return self();
		}

		public MarketIndexDTO build() {
			return new MarketIndexDTO(this);
		}

		protected Builder self() {
			return this;
		}

	}
}
