package ar.com.pa.collections.company;

import java.time.LocalDate;
import lombok.Data;

@Data
public class Instrument {

	public String title;
	public Double value;
	public LocalDate periodEnding;
		
}
