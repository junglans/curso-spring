package com.curso.springboot.api;

import java.lang.reflect.Method;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;

public class Prueba {

	
	public static void main(String[] args) {
		
		Method[] methods = Expressions.class.getMethods();
		
		for (Method m: methods) {
			
			if ("in".equals(m.getName())) {
				System.out.println(m);
				Class<?>[] clazz = m.getParameterTypes();
				if (clazz.length == 1) {
					if (clazz[0].isAssignableFrom(Expression.class)) {
						System.out.println("NO es lista");
					} else if (clazz[0].isAssignableFrom(Expression[].class)) {
						System.out.println("SI es lista");
					}
				}
			}
		}
	}
}
