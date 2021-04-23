package ar.com.pa.scraping.utils;

import com.google.common.base.Strings;

public class VerifyElement {

	
	public static void verifyFailedShare(String id, String title) {
		verifyNullShare(id, title);
		stringIsNumeric(id);
	}

	public static void verifyNullShare(String id, String title) {

		boolean idNull = Strings.isNullOrEmpty(id);
		boolean titleNull = Strings.isNullOrEmpty(title);

		if (idNull)
			throw new NullPointerException("share with id Null");

		if (titleNull)
			throw new NullPointerException("share with title Null");
	}

	public static void stringIsNumeric(String id) {
		try {
			Integer.parseInt(id);
		} catch (NumberFormatException e) {
			throw e;
		}
	}
}
