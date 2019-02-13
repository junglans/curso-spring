package com.curso.springboot.jpa.services;

import java.lang.reflect.Field;

import javax.annotation.PostConstruct;

import org.springframework.core.ResolvableType;

import com.curso.springboot.jpa.models.dao.IBasicDAO;

public abstract class BasicService {
	
	@SuppressWarnings("rawtypes")
	@PostConstruct
	/**
	 * Buscamos todas los atributos que implementan el interfaz IBasicDAO y les asignamos la clase
	 * de su parámetro genérico.
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	 public void initAfterStartup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		   
		    Field[] fields = getClass().getDeclaredFields();
		    for (Field field: fields) {
		    	ResolvableType rType = ResolvableType.forField(field);
		    	if (field.getType().isAssignableFrom(IBasicDAO.class)) {
		    		 
					Class<?> genericType = rType.getGeneric(0).resolve();
		    		IBasicDAO<?> dao = (IBasicDAO)field.get(this);
		    		dao.setTypeOf(genericType);
		    	}
		    }
			
	 }
}
