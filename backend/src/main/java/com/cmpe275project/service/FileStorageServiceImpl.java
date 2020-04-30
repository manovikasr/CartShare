package com.cmpe275project.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;


import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cmpe275project.config.FileStorageProperties;
import com.cmpe275project.exceptionHandling.FileStorageException;
import com.cmpe275project.exceptionHandling.MyFileNotFoundException;

@Service
public class FileStorageServiceImpl implements FileStorageService{

    private final Path fileStorageLocation;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

   
    public String storeFile(MultipartFile file,Long id) {
    	
        Tika tika = new Tika();
        String detectedType="";
		try {
			   detectedType = tika.detect(file.getBytes());
			   
		} catch (IOException e) {
			System.out.println("Error in File Storage Service "+e.getMessage());
		}
         
		if(detectedType.equals("image/png") || detectedType.equals("image/jpeg") || detectedType.equals("image/jpg"))
		{
			String originalFileName = file.getOriginalFilename();
			
			// Check if the file's name contains invalid characters
            if(originalFileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + originalFileName);
            }
			
			String randomString = String.valueOf(userService.generateAccessCode());
			String fileExtension  = originalFileName.substring(file.getOriginalFilename().lastIndexOf("."));
		         
			    // Normalize file name
		        String fileName = StringUtils.cleanPath(new Date().getTime()+randomString.concat("-"+id+fileExtension));
		
		        try {
		            
		            // Copy file to the target location (Replacing existing file with the same name)
		            Path targetLocation = this.fileStorageLocation.resolve(fileName);
		            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		
		            return fileName;
		            
		        } catch (IOException ex) {
		            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		        }        
		}
		else
		{
			throw new FileStorageException("Sorry! Invalid File " + file.getOriginalFilename());
		}
    
    }
    
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

}
