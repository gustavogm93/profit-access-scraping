package ar.com.pa.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.pa.model.financialsummary.FinancialSummaryDTO;

@Repository
public interface FinancialSummaryRepository extends JpaRepository<FinancialSummaryDTO, Long> {
	
	FinancialSummaryDTO save(long i);
	FinancialSummaryDTO findById(long i);
}
