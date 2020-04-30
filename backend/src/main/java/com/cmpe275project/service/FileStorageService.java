package com.cmpe275project.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
	
	public String storeFile(MultipartFile file, Long id);
	
	public Resource loadFileAsResource(String fileName);
	
}
