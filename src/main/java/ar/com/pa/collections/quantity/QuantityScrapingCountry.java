package ar.com.pa.collections.quantity;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection = "quantity-scraping-country")
public class QuantityScrapingCountry{
	
	@Id
	private final String id;
	@NotNull private Integer marketIndex;
	@NotNull private Integer shareAll;
}
