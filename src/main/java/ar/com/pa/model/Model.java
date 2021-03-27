package ar.com.pa.model;

import java.util.List;
import com.google.common.base.Function;

import ar.com.pa.model.dto.RegionDTO;
public interface Model<V, T extends Property> {

	public List<T> toDTO(List<V> objectBase);
	public List<V> toBase(List<T> objectDTO);
	
	public T toDTO(V objectBase);
	public V toBase(T objectDTO);
	public V toBase(RegionDTO objectDTO);
	

	
}
