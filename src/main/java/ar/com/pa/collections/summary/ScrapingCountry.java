package ar.com.pa.collections.summary;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Field;
import ar.com.pa.collections.country.CountryProp;
import ar.com.pa.collections.marketIndex.MarketIndexProp;
import ar.com.pa.collections.share.ShareProp;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
public class ScrapingCountry {
	
	@Id
	private String _id;
	
	@Field(name = "country")
	@NotNull private CountryProp country;
	
	@Field(name = "market-Index-list")
	private List<MarketIndexProp> marketIndexList;
	
	@Field(name = "share-list")
	private List<ShareProp> shares;
	
	@Field(name = "createdAt")
	private Date createdAt;
	
	@Field(name = "LastModifiedAt")
    private Date lastModifiedAt;
}
