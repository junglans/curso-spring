package com.curso.springboot.jpa.services;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {

	Resource load(String fileName) throws Exception;
	
	String copy(MultipartFile file) throws IOException;
	
	boolean delete(String fileName);
	
	void deleteAll();
	
	void init() throws IOException;
}
