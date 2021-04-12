package ar.com.pa.share;

import java.util.Comparator;

import ar.com.pa.generics.Property;

public class Share extends Property{

	public Share(String code, String title) {
		super(code, title);
	}
	
	public static Comparator<Share> byTitle = Comparator.comparing(Share::getTitle);

}