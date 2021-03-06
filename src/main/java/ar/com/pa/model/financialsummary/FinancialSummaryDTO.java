package ar.com.pa.model.financialsummary;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import ar.com.pa.model.Instrument;

@Component
public class FinancialSummaryDTO implements Summary{
		
	 public List<Instrument> instrumentList;

	@Override
	public void setSummary(List<Instrument> listInstrumentDTO) {
		this.instrumentList = listInstrumentDTO;	
	}

	@Override
	public List<Instrument> getSummary() {
		return this.instrumentList;
	}

	public FinancialSummaryDTO(List<Instrument> instrumentList) {
		super();
		this.instrumentList = instrumentList;
	}

	public FinancialSummaryDTO() {
		super();
	}
}
