package ar.com.pa.model;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Instrument {

	public String title;
	public Integer value;
	public Date periodEnding;
		
}
