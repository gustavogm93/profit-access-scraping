package ar.com.pa.model.props;

import java.util.Comparator;

import ar.com.pa.model.Property;

public class Share extends Property{

	public Share(String code, String title) {
		super(code, title);
	}
	
	public static Comparator<Share> byTitle = Comparator.comparing(Share::getTitle);

}