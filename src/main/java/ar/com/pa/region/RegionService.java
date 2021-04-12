package ar.com.pa.region;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegionService {

	private final RegionRepository regionRepository;

	public List<RegionDTO> getAllRegions() {
		return regionRepository.findAll();
	}

	public void addRegion(RegionDTO regionDTO) {
		regionRepository.save(regionDTO);
	}
	
	public void deleteRegion(String code) {
		if(!regionRepository.existsById(code)) {
			throw new RegionNotFoundException(
                    "Region with id " + code + " does not exists");
		}
			
		regionRepository.deleteById(code);
	}
}
