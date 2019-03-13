package com.curso.springboot.api.builders;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/**
 * Los parametros que se reciben desde el objeto FilterBy tiene formato de string, pero en general
 * representan otro tipo de dato que se indica en el atributo attrType del objeto filter. Además, 
 * este tipo es el que es admitido por la capa de persistencia y el utilizado en las Expression<?>.
 * En esta clase hay métodos que se utilizan para transformar el valor string en el valor indicado
 * por attrType utilizando la información del formato que acompaña opcionalmente.
 * @author jf.jimenez
 *
 */
public class ExpressionValuesFactory {
	/**
	 * Devuelve el string.
	 * @param value
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static final String asString(String value, String format) throws Exception {
		return value;
	}
	/**
	 * Devuelve el tipo Number que resulta de aplicar al value el formato que se informa utilizando
	 * un objeto de la clase DecimalFormat.
	 * @param value
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static final Number asNumber(String value, String format) throws Exception {
		DecimalFormat df = new DecimalFormat(format,new DecimalFormatSymbols(Locale.ENGLISH)) ;
		return df.parse(value);
	}
	/**
	 * Devuelve el tipo Comparable<Date> que resulta de aplicar al value el formato que se informa utilizando
	 * un objeto de la clase SimpleDateFormat
	 * @param value
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static final Comparable<Date> asDate(String value, String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return  sdf.parse(value);
	}
}
