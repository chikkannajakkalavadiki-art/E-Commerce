package com.ecommerce.product_service.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.repository.ProductRepository;

@Service
public class ProductService {

	private ProductRepository repository;
	
	public ProductService(ProductRepository repository) {
		
		this.repository=repository;
	}
	
	public Product createProduct(Product product) {
		return repository.save(product);
	}
	
	
	public List<Product> getProducts() {
		
		return repository.findAll();
	}
	
	public Product getProduct(long id) {
		
		return repository.findById(id).orElse(null);
	}
	
	public Product updateProducts(Product product) {
		
	Product	existing = repository.findById(product.getId()).orElseThrow(() -> 
		new RuntimeException("Invalid Product Id"+product.getId()));
		
	existing.setName(product.getName());
	existing.setDescription(product.getDescription());
	existing.setPrice(product.getPrice());
	existing.setQuantity(product.getQuantity());
		return repository.save(existing);
	}
	
	public void deleteProduct(long id) {
		
		repository.deleteById(id);
	}
	
}
