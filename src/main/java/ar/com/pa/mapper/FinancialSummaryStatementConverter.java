package ar.com.pa.mapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ar.com.pa.enums.financialsummary.FinancialSummaryStatement;

@Service
@Component
@Converter(autoApply = true)
public class FinancialSummaryStatementConverter
		extends PersistableEnumConverter<FinancialSummaryStatement, String> implements AttributeConverter <FinancialSummaryStatement, String>{

	
	public FinancialSummaryStatementConverter() {super();}
	
	   @Override
	    protected FinancialSummaryStatement[] getObject() {
	        return FinancialSummaryStatement.values();
	    }
}
