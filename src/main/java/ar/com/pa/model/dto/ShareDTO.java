package ar.com.pa.model.dto;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import ar.com.pa.model.props.MarketIndexProps;
import ar.com.pa.model.props.ShareProps;
import lombok.Data;

@Data
@Document(collection = "Share")
@Component
public class ShareDTO {

	
	private final ShareProps share;
	
	private int price;
	
	private final List<MarketIndexProps> marketIndexes;
	

	private ShareDTO(Builder builder) {
		share = builder.share;
		marketIndexes = builder.marketIndexes;
	}

	public static class Builder {
		private List<MarketIndexProps> marketIndexes;
		private ShareProps share;

		public Builder() {
		}

		public Builder props(ShareProps share) {
			this.share = share;
			return self();
		}

		public Builder marketIndexes(List<MarketIndexProps> marketIndexes) {
			this.marketIndexes = marketIndexes;
			return self();
		}

		public ShareDTO build() {
			return new ShareDTO(this);
		}

		protected Builder self() {
			return this;
		}

	}
}
