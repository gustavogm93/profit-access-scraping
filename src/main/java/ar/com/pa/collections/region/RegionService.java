package ar.com.pa.collections.region;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RegionService {

	public List<RegionDTO> getAll();
	
	public List<RegionDTO> findByTitle(String title);
	
	public RegionDTO findByCode(String code) throws Exception;
	
	public void add(RegionDTO regionDTO);
	
	public void addAll(List<RegionDTO> regionDTOList);
	
	public void delete(String code);
	public CompletableFuture<Boolean> updateCoverageRegion(String regionCode) throws Exception;
}
