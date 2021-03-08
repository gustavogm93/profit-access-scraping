package ar.com.pa.model;

import java.util.Date;
import lombok.Data;

@Data
public class Instrument {

	public String title;
	public Integer value;
	public Date periodEnding;
		
}
