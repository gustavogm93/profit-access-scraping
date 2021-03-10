package ar.com.pa.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapperUtilsImpl implements MapperUtils {

	private static Logger logger = LoggerFactory.getLogger(MapperUtilsImpl.class);


	@Override
	public LocalDate toDate(String date) {

		try {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d, u", Locale.ENGLISH);
			LocalDate localDatePeriod = LocalDate.parse(date, dateFormatter);
			return localDatePeriod;
		} catch (Exception e) {
			logger.error("Date erronea");
			return null;
		}
	}
	
	@Override
	public LocalDate convertToDateYYYY_MM_DD(String year, String monthAndDay) {
		
		 String[] dayMonthAr = monthAndDay.split("[/-]", 0);

		String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec" };
		int day = Integer.parseInt(dayMonthAr[1]);
		String monthWord = months[day - 1];
		String date = String.format("%s %d, %s", monthWord, day, year);

		return toDate(date);

	}

	@Override
	public int stringToNum(String s) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public String addFormat(String old, String next) {
		return String.format("%s %s", old, next);
	}

}
