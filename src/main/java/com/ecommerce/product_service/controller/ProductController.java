package com.ecommerce.product_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.services.ProductService;
import org.springframework.web.bind.annotation.PutMapping;


@RequestMapping("/api")
@RestController
public class ProductController {
	
private ProductService service;

	ProductController(ProductService service){
		
		this.service = service;
	}
	
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody Product product){
		
		return ResponseEntity.ok().body(service.createProduct(product));
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts(){
		return ResponseEntity.ok().body(service.getProducts());
	}
	


	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable long id) {
	    Product product = service.getProduct(id);

	    if (product == null) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.ok(product);
	}
	
	@PutMapping("/product")
	public ResponseEntity<Product> updateProducts(Product product){
		
		return ResponseEntity.ok().body(service.updateProducts(product));
	}
	
	@DeleteMapping("/product/{id}")
	public String deleteProduct(@PathVariable int id){
		service.deleteProduct(id);
		
		return "Prodcut Deleted Successfully";
	}
	
}
