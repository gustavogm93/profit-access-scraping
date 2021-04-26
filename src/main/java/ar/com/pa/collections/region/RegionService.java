package ar.com.pa.collections.region;

import java.util.List;

public interface RegionService {

	public List<RegionDTO> getAll();
	
	public RegionDTO findByTitle(String title);
	
	public List<RegionDTO> findByCode(String code);
	
	public void add(RegionDTO regionDTO);
	
	public void addAll(List<RegionDTO> regionDTOList);
	
	public void delete(String code);
	
}
