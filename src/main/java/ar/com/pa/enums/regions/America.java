package ar.com.pa.enums.regions;

public enum America {

	Argentina("argentina"), 

	Mexico("mexico"), 

	Colombia("colombia"), 

	Canada("canada"), 

	Chile("chile"), 

	Brazil("brazil"), 

	UnitedStates("united-states"), 

	Jamaica("jamaica"), 

	Venezuela("venezuela");
	
	public String title;
	
	America(String title) {
		this.title = title;
	};
	
	
}
