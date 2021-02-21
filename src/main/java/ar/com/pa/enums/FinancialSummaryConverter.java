package ar.com.pa.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.stereotype.Service;
import java.util.stream.Stream;

@Service
@Converter(autoApply = true)
public class FinancialSummaryConverter implements AttributeConverter<FinancialSummary, String> {

	/**
	 * Convert FinancialSummary object for mapping ORM model.
	 */
	public String convertToDatabaseColumn(FinancialSummary financialSummary) {
		if (financialSummary == null) {
			return null;
		}
		return financialSummary.getTitle();
	}

	/**
	 * Get FinancialSummaryStatement element by summary title, If the summary title
	 * not matched with any element, then throw IllegalArgumentException.
	 * 
	 * @return A FinancialSummaryStatement.
	 */
	public FinancialSummary convertToEntityAttribute(String summaryTitle) {

		if (summaryTitle == null) {
			return null;
		}
		return Stream.of(FinancialSummary.values()).filter(c -> c.getTitle().equalsIgnoreCase(summaryTitle)).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}
