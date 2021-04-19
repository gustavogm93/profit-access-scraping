package ar.com.pa.region;

import java.util.Comparator;

public class RegionComparator {

	public static Comparator<RegionDTO> byTitle = Comparator.comparing(RegionDTO::getTitle);
	public static Comparator<RegionDTO> byCode = Comparator.comparing(RegionDTO::getTitle);
}
