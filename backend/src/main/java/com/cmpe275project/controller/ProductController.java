package com.cmpe275project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275project.model.Product;
import com.cmpe275project.model.StoreProduct;
import com.cmpe275project.requestObjects.ProductStoreRequest;
import com.cmpe275project.responseObjects.ProductResponse;
import com.cmpe275project.service.ProductService;
import com.cmpe275project.service.StoreService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private StoreService storeService;
	
	@PostMapping("")
	public ResponseEntity<?> addProduct(@Valid @RequestBody Product productRequest, Errors errors)
	{
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProductResponse response = new ProductResponse();
		
        if(errors.hasErrors()) {
			
			Map<String,String> errorMap = new HashMap<String,String>();
			
			 for (ObjectError error : errors.getAllErrors()) {
			       String fieldError = ((FieldError) error).getField();
			       errorMap.put(fieldError, error.getDefaultMessage());
			   }
			 response.setErrors(errorMap);
			 response.setMessage("Unable to add Product");
			
			return new ResponseEntity<>(response, status);
		}
		
        if(!productService.chkProductNameExists(productRequest.getProduct_name())){
        	
        	if(!productService.chkSKUExists(productRequest.getSku())) {
        	      
        		productService.add(productRequest);
            	response.setMessage("Product Successfully Added");
            	response.setStore(productRequest);
        		
        	}else {
        		status = HttpStatus.CONFLICT;
            	response.setMessage("SKU Already In Use");
        	}
        	
        }else {
        	status = HttpStatus.CONFLICT;
        	response.setMessage("Product Name Already In Use");
        }
        
		return new ResponseEntity<>(response,status);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productRequest, Errors errors)
	{
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProductResponse response = new ProductResponse();
		
		if (errors.hasErrors()) {

			Map<String, String> errorMap = new HashMap<String, String>();

			for (ObjectError error : errors.getAllErrors()) {
				String fieldError = ((FieldError) error).getField();
				errorMap.put(fieldError, error.getDefaultMessage());
			}
			response.setErrors(errorMap);
			response.setMessage("Unable to update Product");

			return new ResponseEntity<>(response, status);
		}
		
		if(!productService.isProductIdExists(id)) {
			status = HttpStatus.NOT_FOUND;
			response.setMessage("Product Id in Invalid");
			return new ResponseEntity<>(response,status);
		}
		
		if(!productService.isProductNameAvailable(productRequest.getProduct_name(), id)) {
		   
			if(!productService.isSKUAvailable(productRequest.getSku(),id)) {
				
				Product product = productService.getProductInfoById(id);
				product.setProduct_name(productRequest.getProduct_name());
				product.setSku(productRequest.getSku());
				product.setProduct_desc(productRequest.getProduct_desc());
				product.setPrice(productRequest.getPrice());
				product.setUnit_type(productRequest.getUnit_type());
				product.setProduct_brand(productRequest.getProduct_brand());
				product.setProduct_img(productRequest.getProduct_img());
				
				productService.edit(product);
				status         = HttpStatus.OK;
				response.setMessage("Product Successfully Edited");
		        response.setStore(product);	
				
			}else {
				status = HttpStatus.CONFLICT;
            	response.setMessage("SKU Already In Use");
			}
					
			
		}else {
			status = HttpStatus.CONFLICT;
			response.setMessage("Product Name is Already in Use");
			return new ResponseEntity<>(response,status);
		}
		
		return new ResponseEntity<>(response,status);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProductInfo(@PathVariable Long id)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		Product product = null;
		ProductResponse response = new ProductResponse();
		if(productService.isProductIdExists(id)) {
			
			product = productService.getProductInfoById(id);
			response.setMessage("Product Details");
			response.setStore(product);
		    status = HttpStatus.OK;
		}else {
			response.setMessage("Product Details Not Available");
		}
		
		return new ResponseEntity<>(response,status);
	}
	
	@PostMapping("/map_products_with_stores/{store_id}")
	public ResponseEntity<?> mapProductsWithStores(@PathVariable Long store_id, @RequestParam Set<Long> product_ids)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProductResponse response = new ProductResponse();
		if(storeService.isStoreIdExists(store_id)) {
			
			List<StoreProduct> storeProductList = new ArrayList<>();
			
			for(long product_id:product_ids) {
				
				if(!productService.isProductIdExists(product_id))
				 {
					response.setMessage("Product Id "+product_id +" Not Available ");
					return new ResponseEntity<>(response,status);
				 }
				
				if(storeService.chkStoreProductExists(store_id, product_id)) {
					status = HttpStatus.BAD_REQUEST;
					response.setMessage("Store is Already Mapped With Products, use Update API");
					return new ResponseEntity<>(response,status);
				}
				
				StoreProduct storeProduct = new StoreProduct(store_id,product_id);
				storeProductList.add(storeProduct);
			}
			
			storeService.addAllStoreProducts(storeProductList);
			
			response.setMessage("Store Products Successfully Added");
			status = HttpStatus.OK;
		}else {
			response.setMessage("Store Details Not Available");
		}
		
		return new ResponseEntity<>(response,status);
	}
	
	@PutMapping("/map_products_with_stores/{store_id}")
	public ResponseEntity<?> updateProductsWithStoresMapping(@PathVariable Long store_id, @RequestParam Set<Long> product_ids)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProductResponse response = new ProductResponse();
		if(storeService.isStoreIdExists(store_id)) {
			List<StoreProduct> storeProductList = new ArrayList<>();
			
			for(long product_id:product_ids) {
				
				if(!productService.isProductIdExists(product_id))
				 {
					response.setMessage("Product Id "+product_id +" Not Available ");
					return new ResponseEntity<>(response,status);
				 }
				StoreProduct storeProduct = new StoreProduct(store_id,product_id);
				storeProductList.add(storeProduct);
			}
			
			List<StoreProduct> existingStoreProducts = storeService.getStoreProductList(store_id);
			
			storeService.deleteAllStoreProducts(existingStoreProducts);
			storeService.addAllStoreProducts(storeProductList);
			
			response.setMessage("Store Products Successfully Updated");
			
		    status = HttpStatus.OK;
		}else {
			response.setMessage("Store Details Not Available");
		}
		
		return new ResponseEntity<>(response,status);
	}
	
}
