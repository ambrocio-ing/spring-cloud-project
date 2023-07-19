package com.amegdev.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.amegdev.model.Customer;

@FeignClient(name = "customer-service", path = "/customers")
//@RequestMapping("/customers")
public interface ICustomerClient {
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable(name = "id") Integer id);
	
}
