package ar.com.pa.queue;

import java.io.Serializable;

import ar.com.pa.collections.country.CountryProp;
import ar.com.pa.collections.region.RegionProp;
import lombok.Data;

@Data
public class SystemMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private CountryProp country;
    private RegionProp region;
    
	public SystemMessage(CountryProp country, RegionProp region) {
		this.country = country;
		this.region = region;
	}
    

}