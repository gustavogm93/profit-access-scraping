package ar.com.pa.mapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.stereotype.Service;
import ar.com.pa.enums.financialsummary.FinancialSummary;


@Service
@Converter(autoApply = true)
public class FinancialSummaryConverter extends PersistableEnumConverter<FinancialSummary, String>  implements AttributeConverter <FinancialSummary, String> {	
	
	public FinancialSummaryConverter() {
		super();
	}
	   @Override
	    protected FinancialSummary[] getObject() {
	        return FinancialSummary.values();
	    }
}
