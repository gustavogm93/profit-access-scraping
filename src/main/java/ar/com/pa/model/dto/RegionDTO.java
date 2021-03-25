package ar.com.pa.model.dto;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import ar.com.pa.model.props.CountryProps;
import ar.com.pa.model.props.RegionProps;
import lombok.Data;

@Data
@Document(collection = "Region")
@Component
public class RegionDTO {

	private final RegionProps region;

	private final List<CountryProps> countries;

	private RegionDTO(Builder builder) {
		countries = builder.countries;
		region = builder.region;
	}

	public static class Builder {

		private RegionProps region;
		private List<CountryProps> countries;

		public Builder() {
		}

		public Builder props(RegionProps region) {
			this.region = region;
			return self();
		}

		public Builder countries(List<CountryProps> countries) {
			this.countries = countries;
			return self();
		}

		public RegionDTO build() {
			return new RegionDTO(this);
		}

		protected Builder self() {
			return this;
		}

	}

}
