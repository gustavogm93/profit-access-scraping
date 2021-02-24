package ar.com.pa.enums.financialsummary;
import org.springframework.stereotype.Service;

@Service
public interface FinancialSummaryProperty<T> {
	
	public abstract T getTitle();
	
}
