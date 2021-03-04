package ar.com.pa.model.financialsummary;

import java.util.List;

import ar.com.pa.model.Instrument;

public interface Summary{

	public void setSummary(List<Instrument> listInstrumentDTO);
	
	public List<Instrument> getSummary();
	
}