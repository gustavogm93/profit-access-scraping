package ar.com.pa.country;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CountryService {

	private final CountryRepository countryRepository;

	public List<CountryDTO> getAllCountries() {
		return countryRepository.findAll();
	}

	public void addCountry(CountryDTO countryDTO) {
		countryRepository.save(countryDTO);
	}
	
	public void deleteCountry(String code) {
		if(!countryRepository.existsById(code)) {
			throw new CountryNotFoundException(
                    "Country with id " + code + " does not exists");
		}
			
		countryRepository.deleteById(code);
	}
}
