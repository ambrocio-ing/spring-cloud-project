package com.amegdev.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.amegdev.model.Customer;

@Component
public class CustomerHystrixFallbackFactory implements ICustomerClient {

	@Override
	public ResponseEntity<Customer> getCustomer(Integer id) {
		Customer customer = new Customer();
		customer.setFirstName("none");
		customer.setLastName("none");
		customer.setEmail("none");
		customer.setPhotoUrl("none");
		return ResponseEntity.ok(customer);
	}

}
