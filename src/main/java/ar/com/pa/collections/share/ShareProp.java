package ar.com.pa.collections.share;

import java.util.Comparator;

import ar.com.pa.generics.Property;

public class ShareProp extends Property{

	public ShareProp(String code, String title) {
		super(code, title);
	}
	
	public static Comparator<ShareProp> byTitle = Comparator.comparing(ShareProp::getTitle);

}