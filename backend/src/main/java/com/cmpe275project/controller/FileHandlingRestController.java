package com.cmpe275project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cmpe275project.model.Product;
import com.cmpe275project.model.User;
import com.cmpe275project.responseObjects.FileUploadResponse;
import com.cmpe275project.service.FileStorageService;
import com.cmpe275project.service.ProductService;

@RestController
public class FileHandlingRestController {

	//private static final Logger logger = LoggerFactory.getLogger(FileHandlingRestController.class);
	
    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private ProductService productService; 
    

    @PostMapping("/upload/product/{product_id}")
    public ResponseEntity<?> uploadFile(@PathVariable Long product_id, @RequestParam("file") MultipartFile file,HttpServletRequest request) {
    	
    	FileUploadResponse response = new FileUploadResponse();
    	HttpStatus status = HttpStatus.BAD_GATEWAY;
    	
    	if(!productService.isProductIdExists(product_id)) {
    		response.setMessage("Product Id does not exists....");
    	}else {
    	       
    		String fileName = fileStorageService.storeFile(file,product_id);
            
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();
            
            Product product = productService.getProductInfoById(product_id);
            product.setProduct_img(fileName);
            
            productService.edit(product);
            response.setMessage("Image Uploading Successful....");
            response.setFileName(fileName);
    		status = HttpStatus.OK;
    	}
    	
        
        return new ResponseEntity<>(response,status);
    }

        
    @GetMapping("/image/product/{product_id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long product_id, HttpServletRequest request) {
    	
    	if(!productService.isProductIdExists(product_id)) {
    		return new ResponseEntity<>("Product Id Not Exists....",HttpStatus.BAD_REQUEST);
    	}
    	
    	Product product = productService.getProductInfoById(product_id);
    	
    	if(product.getProduct_img()==null || product.getProduct_img().isEmpty()) {
    		return new ResponseEntity<>("Product Image Not Exists",HttpStatus.NOT_FOUND);
    	}
    	
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(product.getProduct_img());

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            //logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_TYPE, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
