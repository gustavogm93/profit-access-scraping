package ar.com.pa.repository;
import ar.com.pa.model.financial_summary.FinancialSummaryDTO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialSummaryRepository extends JpaRepository<FinancialSummaryDTO, Long> {
	
	FinancialSummaryDTO save(long i);
	FinancialSummaryDTO findById(long i);
}
