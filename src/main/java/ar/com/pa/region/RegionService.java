package ar.com.pa.region;

import java.util.List;

public interface RegionService {

	public List<RegionDTO> getAll();
	
	public List<RegionDTO> findByTitle(String title);
	
	public void add(RegionDTO regionDTO);
	
	public void addAll(List<RegionDTO> regionDTOList);
	
	public void delete(String code);
	
}
