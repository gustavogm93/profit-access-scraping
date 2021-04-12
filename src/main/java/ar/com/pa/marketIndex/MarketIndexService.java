package ar.com.pa.marketIndex;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MarketIndexService {

	private final MarketIndexRepository marketIndexRepository;

	public List<MarketIndexDTO> getAllMarketIndex() {
		return marketIndexRepository.findAll();
	}

	public void addRegion(MarketIndexDTO marketIndexDTO) {
		marketIndexRepository.save(marketIndexDTO);
	}
	
	public void deleteRegion(String code) {
		if(!marketIndexRepository.existsById(code)) {
			throw new MarketIndexNotFoundException(
                    "Market index with id " + code + " does not exists");
		}
			
		marketIndexRepository.deleteById(code);
	}
}
