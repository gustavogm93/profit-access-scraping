package ar.com.pa.enums;


public enum RegionConstant {

	 America("1", "America"),
	 Europe("2", "Europe"),
	 Asia_Pacific("3", "Asia-Pacific"),
	 Middle_East("4", "Middle-East"),
	 Africa("5", "Africa");
	
	public String code;
	public String title;
	
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
