package ar.com.pa.collections.quantity;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection = "quantity-scraping-region")
public class QuantityScrapingRegion {
	
	@Id
	private final String id;
	@NotNull private List<QuantityScrapingCountry> country;

}
