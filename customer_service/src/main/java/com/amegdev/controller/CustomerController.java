package com.amegdev.controller;

import java.util.ArrayList;
import java.util.List;
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

import com.amegdev.entity.Customer;
import com.amegdev.entity.Region;
import com.amegdev.service.ICustomerService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private ICustomerService customerService;
	
	@GetMapping
	public ResponseEntity<List<Customer>> listAllCustomers(@RequestParam(name = "regionId", required = false) Integer regionId){
		
		List<Customer> customers = new ArrayList<>();
		if(null == regionId) {
			customers = customerService.getCustomerAll();
			if(customers.isEmpty()) {
				//return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin datos que mostrar");
				return ResponseEntity.noContent().build();
			}
		}
		else {
			Region region = new Region();
			region.setId(regionId);
			customers = customerService.getCustomerByRegion(region);
			if(null == customers) {
				log.error("Clientes para la region {} no encontrado.", regionId);
				return ResponseEntity.notFound().build();
			}
		}
		
		return ResponseEntity.ok(customers);		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable(name = "id") Integer id){
		
		Customer customer = customerService.getCustomer(id);
		if(null == customer) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(customer);		
	}
	
	@PostMapping
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result){
		
		if(result.hasErrors()) {
			List<String> listErrors = result.getFieldErrors().stream()
					.map(error -> String.format("Error en %s : %s", error.getField(), error.getDefaultMessage()))
					.collect(Collectors.toList());
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, listErrors.toString());
		}
		
		Customer customerDB = customerService.createCustomer(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(customerDB);
		
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable(name = "id") Integer id, @RequestBody Customer customer){
		
		Customer currentCustomer = customerService.getCustomer(id);
		if(null == currentCustomer) {
			return ResponseEntity.notFound().build();
		}
		
		customer.setId(currentCustomer.getId());
		currentCustomer = customerService.updateCustomer(customer);
		return ResponseEntity.ok(currentCustomer);		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable(name = "id") Integer id){
		
		Customer customer = customerService.getCustomer(id);
		if(null == customer) {
			return ResponseEntity.notFound().build();
		}
		
		customer = customerService.deleteCustomer(customer);
		return ResponseEntity.ok(customer);		
	}
	
}
