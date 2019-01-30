package com.curso.springboot.jpa.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GlobalValues {

	public static String UPLOADFOLDER;

	@Value("${application.uploads.folder}")
	public  void setUPLOADFOLDER(String uPLOADFOLDER) {
		UPLOADFOLDER = uPLOADFOLDER;
	}


	 
	
}
