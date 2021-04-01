package ar.com.pa.utils;

public class Msg {

	public final static String seleniumExecutor = "Executing selenium Chrome Driver";
	public final static String fetchingCountry = "Getting country";

	public static String compound(Object object, String msg) {

		return String.format(("%s: %s"),msg, object.toString() );
	}

}
