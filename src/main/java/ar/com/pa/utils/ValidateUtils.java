package ar.com.pa.utils;

import ar.com.pa.enums.utils.SummaryType;

public interface ValidateUtils {
	

	/**
	 * Returns {@code boolean} value if {@code String} object is null
	 *  or can become into a {@code T extends Number} 
	 * @param value of string {@code String}
	 * @return the {@code boolean}.
	 */
	public boolean isNumOrEmpty(String strNum);
	
	public boolean isString(String str);

	/**
	 * check if value {@code isSummaryObject(str)} OR {@code NumOrEmpty()}; 
	 * @param string value
	 * @param summaryType st
	 * @return returns {@code boolean}.
	 */ 
	public boolean isSummaryModelValue(String str);
	/**
	 * check if value {@code Summary<T>.getTitle().equalsIgnoreCase(str)}
	 * @param string value
	 * @param summaryType st
	 * @return returns {@code boolean}.
	 */ 
	public boolean isSummaryObject(String str);

	public boolean isDate(String s);
	
}
/**
 * Returns the package name of {@code classFullName} according to the Java Language Specification
 * (section 6.7). Unlike {@link Class#getPackage}, this method only parses the class name, without
 * attempting to define the {@link Package} and hence load files.
 */