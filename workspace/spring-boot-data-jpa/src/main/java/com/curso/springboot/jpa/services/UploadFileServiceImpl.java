package com.curso.springboot.jpa.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

 
import com.curso.springboot.jpa.utils.GlobalValues;

@Service
public class UploadFileServiceImpl implements IUploadFileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadFileServiceImpl.class);
	
	@Override
	public Resource load(String fileName) throws Exception {
		
		Path pathPhoto = getPath(fileName);
		Resource resource = null;
		try {

			resource = new UrlResource(pathPhoto.toUri());
			if (!resource.exists() && !resource.isReadable()) {
				throw new RuntimeException("La imagen no exite o no es accesible.");
			}

		} catch (MalformedURLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception(e);
		}
		return resource;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		
		String uniquefileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getPath(uniquefileName);
		Path rootAbsolutePath = rootPath.toAbsolutePath();
		Files.copy(file.getInputStream(), rootAbsolutePath);
		
		return uniquefileName;

	}

	@Override
	public boolean delete(String fileName) {
		
		Path photoPath = getPath(fileName);
		File file = photoPath.toFile();
		if (file.exists() && file.canRead()) {
			return file.delete();
		}
		return false;
	}

	public Path getPath(String fileName) {
		return Paths.get(GlobalValues.UPLOADFOLDER).resolve(fileName).toAbsolutePath();
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(GlobalValues.UPLOADFOLDER).toFile());
		
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(GlobalValues.UPLOADFOLDER));
	}
}
