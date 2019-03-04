package com.curso.springboot.api.builders;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TypesFactory {

	public static final String asString(String value, String format) throws Exception {
		return value.toString();
	}
	
	public static final Number asNumber(String value, String format) throws Exception {
		DecimalFormat df = new DecimalFormat(format,new DecimalFormatSymbols(Locale.ENGLISH)) ;
		return df.parse(value);
	}
	
	public static final Comparable<Date> asDate(String value, String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return  sdf.parse(value);
	}
}
