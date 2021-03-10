package ar.com.pa.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapperUtils {

    private static Logger logger = LoggerFactory.getLogger(MapperUtils.class);

	public static int convertToValue(String[] value, int index) {
		String valueToAdd = value[index].substring(2,value[index].length());
		return Integer.parseInt(valueToAdd);
	}

	public static LocalDate convertToDate(String[] value,String[] periods ,int index) {
		
		int periodIndex = Integer.parseInt(value[index].substring(0,1));
		String date = periods[periodIndex - 1];

		LocalDate localDatePeriod = LocalDate.parse(date);
	    
		return localDatePeriod;
	}
	
	public static LocalDate toDate(String date) {

		try {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d, u", Locale.ENGLISH);
			LocalDate localDatePeriod = LocalDate.parse(date, dateFormatter);
			return localDatePeriod;
		}catch (Exception e) {
			logger.error("Date erronea");
			return null;
		}
	}
	
	
	public static LocalDate convertToFormatDate(String year, String month) {
		
		String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};
		int day = Integer.parseInt(month.substring(3,5));
		String monthWord = months[day - 1];
		String date = String.format("%s %d, %s",monthWord, day, year);
		
		return toDate(date);
		
	}
	
	public static int stringToNum(String s) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return -1;
		}	
	}
	
	public static String addFormat(String old, String next) {
		return String.format("%s %s", old, next);
	}
	
	
	
}
