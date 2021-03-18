package ar.com.pa.enums.regions;

public enum MiddleEast {
	
	Israel("israel"), 

	Jordan("jordan"), 

	Oman("oman"), 

	Kuwait("kuwait"), 

	Egypt("egypt"), 

	Qatar("qatar"), 

	SaudiArabia("saudi-arabia"), 

	Lebanon("lebanon"), 

	Iraq("iraq"), 

	PalestinianTerritory("palestine"), 

	UnitedArabEmirates("dubai"), 

	Bahrain("bahrain");
	

	public String title;

	MiddleEast(String title) {
		this.title = title;
	};
}
