package com.curso.springboot.api.builders;

public class TypesFactory {

	public static final Class<?> asString()  {
		return java.lang.String.class;
	}
	
	public static final Class<?> asNumber() {
		return java.lang.Number.class;
	}
	
	public static final Class<?> asDate() {
		return java.util.Date.class;
	}
}
