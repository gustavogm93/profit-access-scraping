package ar.com.pa.utils;

import ar.com.pa.model.Property;

public class Msg {

	public final static String seleniumExecutor = "Executing selenium Chrome Driver";
	public final static String startDriver = "Starting Crhome Driver...";
	public final static String fetchingCountry = "Getting country";
	public final static String marketIndexList = "Getting to market Index List";
	public static < T extends Property > String compound(T object, String msg) {

		return String.format(("%s: %s"),msg, object.getTitle() );
	}

}
