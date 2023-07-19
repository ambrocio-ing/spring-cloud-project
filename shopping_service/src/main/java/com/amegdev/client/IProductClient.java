package com.amegdev.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.amegdev.model.Product;

@FeignClient(name = "product-service", path = "/products")
//@RequestMapping("/products")
public interface IProductClient {
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable(name = "id") Integer id);
	
	@GetMapping("/{id}/stock")
	public ResponseEntity<Product> updateStockProduct(@PathVariable Integer id, @RequestParam(name = "quantity", required = true) Double quantity);
	
}
