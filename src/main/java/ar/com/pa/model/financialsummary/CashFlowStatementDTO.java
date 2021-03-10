package ar.com.pa.model.financialsummary;

import java.util.List;
import ar.com.pa.model.Instrument;

public class CashFlowStatementDTO implements Summary{
	
 public List<Instrument> instrumentList;

@Override
public void setSummary(List<Instrument> listInstrumentDTO) {
	this.instrumentList = listInstrumentDTO;	
}

@Override
public List<Instrument> getSummary() {
	return this.instrumentList;
}

public CashFlowStatementDTO(List<Instrument> instrumentList) {
	super();
	this.instrumentList = instrumentList;
}

public CashFlowStatementDTO() {
	super();
}


}