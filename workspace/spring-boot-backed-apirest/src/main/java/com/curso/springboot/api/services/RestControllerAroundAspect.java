package com.curso.springboot.api.services;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
 

@Aspect
@Component
public class RestControllerAroundAspect {

	@Around("execution(* com.curso.springboot.api.controller.BaseCRUDRestControler.*(..))")
	public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
		 
		ResponseEntity<?> value = null;
		try {
			
			value = (ResponseEntity<?>)proceedingJoinPoint.proceed();
			
		} catch (DataAccessException e) {
			
			Map<String, Object> response = new HashMap<>();
			response.put("message", "Error al eliminar el entidad en la base de datos");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}  catch (NoSuchElementException e) {
			
			Map<String, Object> response = new HashMap<>();
			response.put("message", "La entidad no existe en la base de datos");
			response.put("error", "Entity not found");
			response.put("status", HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			
		} catch (Throwable e) {

			Map<String, Object> response = new HashMap<>();
			response.put("message", "Error al eliminar el entidad en la base de datos");
			response.put("error", e.getMessage());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
		return value;
	}
	
 	@Around("execution(* com.curso.springboot.api.controller.*.update(..))")
	public Object updateAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
 		return this.aroundAdvice(proceedingJoinPoint);
	}
}
