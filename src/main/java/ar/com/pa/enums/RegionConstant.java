package ar.com.pa.enums;

import java.util.Arrays;


import com.google.common.collect.ImmutableList;

public enum RegionConstant {

	 America("1", "America"),
	 Europe("2", "Europe"),
	 Asia_Pacific("3", "Asia-Pacific"),
	 Middle_East("4", "Middle-East"),
	 Africa("5", "Africa");
	
	public static final ImmutableList<RegionConstant> values = ImmutableList.copyOf(Arrays.asList(RegionConstant.values()));
	
	private final String code;
	
	private final String title;
	
	
	
	public String getCode() {
		return this.code;
	}
	public String getTitle() {
		return this.title;
	}
	private RegionConstant(String code, String title) {
		this.code = code;
		this.title = title;
	}
}
