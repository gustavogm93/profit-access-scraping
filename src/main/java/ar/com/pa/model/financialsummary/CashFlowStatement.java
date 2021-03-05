package ar.com.pa.model.financialsummary;

import java.util.List;
import ar.com.pa.model.Instrument;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("CashFlow")
public class CashFlowStatement implements Summary{
	
 public List<Instrument> instrumentList;

@Override
public void setSummary(List<Instrument> listInstrumentDTO) {
	this.instrumentList = listInstrumentDTO;	
}

@Override
public List<Instrument> getSummary() {
	return this.instrumentList;
}

public CashFlowStatement(List<Instrument> instrumentList) {
	super();
	this.instrumentList = instrumentList;
}

public CashFlowStatement() {
	super();
}


}