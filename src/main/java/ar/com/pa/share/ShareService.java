package ar.com.pa.share;

import java.util.List;

public interface ShareService {

	public List<ShareDTO> getAllShare();

	public void addShare(ShareDTO ShareDTO);
	
	public void addAllShare(List<ShareDTO> ShareDTOList);
	
	public void deleteShare(String code);
	
	public List<ShareDTO> findByTitle(String title);
	
}
