package com.curso.springboot.api;

import java.lang.reflect.Field;

import com.curso.springboot.api.entity.QClientEntity;

public class Prueba {

	
	public static void main(String[] args ) throws  Exception {
		QClientEntity qclient = QClientEntity.clientEntity;
		Field filterField = qclient.getClass().getField("name");
		Object fieldPath = ((Object) filterField.get(qclient));
		
		 

	}
}
