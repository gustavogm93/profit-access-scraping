package ar.com.pa.queue;

import java.io.Serializable;

import Region.Region;
import ar.com.pa.country.Country;
import lombok.Data;

@Data
public class SystemMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Country country;
    private Region region;
    
	public SystemMessage(Country country, Region region) {
		this.country = country;
		this.region = region;
	}
    

}