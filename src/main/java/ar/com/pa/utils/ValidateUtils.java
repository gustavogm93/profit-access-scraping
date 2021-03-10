package ar.com.pa.utils;

import ar.com.pa.enums.utils.SummaryType;

public interface ValidateUtils {

	public boolean isNumOrEmpty(String strNum);

	public boolean isString(String str);

	public boolean isSummaryModelValue(String s, SummaryType summaryPerYear);

	public boolean isSummaryObject(String obj, SummaryType summaryPerYear);

	public boolean isDate(String s);
	
}
