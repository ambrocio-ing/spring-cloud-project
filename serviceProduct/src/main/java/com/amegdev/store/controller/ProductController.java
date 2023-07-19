package com.amegdev.store.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.amegdev.store.entity.Category;
import com.amegdev.store.entity.Product;
import com.amegdev.store.service.IProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private IProductService productService;
	
	@GetMapping
	public ResponseEntity<List<Product>> listProducts(){
		
		List<Product> prods = productService.getAll();
		if(prods.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(prods);
		
	}
	
	@GetMapping("/by-cat/{category_id}")
	public ResponseEntity<List<Product>> listProductsByCategory(@PathVariable Integer category_id){
		List<Product> prods = new ArrayList<>();
		
		if(category_id == null) {
			prods = productService.getAll();
		}
		else {
			prods = productService.getByCategory(Category.builder().id(category_id).build());
		}		
		
		if(prods.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(prods);		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable(name = "id") Integer id){		
		
		Product pro = productService.get(id);
		if(null == pro){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(pro);		
	}
	
	@PostMapping
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result){
		
		if(result.hasErrors()) {
			List<String> resultErrors = result.getFieldErrors().stream()
					.map(e -> String.format("Error: %s : %s", e.getField(), e.getDefaultMessage()))
					.collect(Collectors.toList());
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, resultErrors.toString());
		}
		
		Product pro = null;	
		
		try {
			pro = productService.create(product);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pro);		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product product){
		
		Product pro = productService.get(id);
		
		if(null == pro)
			return ResponseEntity.notFound().build();
		
		try {			
			pro.setName(product.getName());
			pro.setDescription(product.getDescription());
			pro.setCategory(product.getCategory());
			pro = productService.update(pro);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pro);		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Product> deleteProd(@PathVariable Integer id){
		
		Product pro = productService.get(id);
		if(null == pro)
			return ResponseEntity.notFound().build();
		
		pro = productService.delete(id);
		return ResponseEntity.ok(pro);		
	}
	
	@GetMapping("/{id}/stock")
	public ResponseEntity<Product> updateStockProduct(@PathVariable Integer id, @RequestParam(name = "quantity", required = true) Double quantity){
		
		Product pro = productService.updateStock(id, quantity);
		if(pro == null)
			return ResponseEntity.notFound().build();	
		
		return ResponseEntity.ok(pro);
	}
}
