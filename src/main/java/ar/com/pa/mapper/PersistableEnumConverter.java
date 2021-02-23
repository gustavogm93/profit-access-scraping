package ar.com.pa.mapper;

import javax.persistence.AttributeConverter;

import org.springframework.stereotype.Service;
import ar.com.pa.enums.financialsummary.FinancialSummaryProperty;


@Service
public abstract class PersistableEnumConverter <E extends FinancialSummaryProperty<T>, T> implements AttributeConverter<E,T> {

    protected E[] enumArray;

    protected PersistableEnumConverter() {
        this.enumArray = getObject();
    }

    @Override
    public T convertToDatabaseColumn(E attribute) {
        return attribute.getTitle();
    }
    
    @Override
    public E convertToEntityAttribute(T dbData) {

    	for (E enumObject : enumArray) {
    		if (enumObject.getTitle().toString().equalsIgnoreCase(dbData.toString()))
    			return enumObject;
    	}
    	return null;
    }
    
   abstract protected E[] getObject();
    
}
