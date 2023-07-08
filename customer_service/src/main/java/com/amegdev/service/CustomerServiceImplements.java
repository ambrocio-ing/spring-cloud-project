package com.amegdev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amegdev.entity.Customer;
import com.amegdev.entity.Region;
import com.amegdev.repository.ICustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServiceImplements implements ICustomerService {
	
	@Autowired
	private ICustomerRepository customerRepository;
	
	@Override
	public List<Customer> getCustomerAll() {
		
		return customerRepository.findAll();
	}

	@Override
	public List<Customer> getCustomerByRegion(Region region) {
		
		return customerRepository.findByRegion(region);
	}

	@Override
	public Customer createCustomer(Customer customer) {
		
		Customer customerDB = customerRepository.findByNumberId(customer.getNumberId());
		if(customerDB != null) {
			return customerDB;
		}
		
		customer.setState("CREATED");
		customerDB = customerRepository.save(customer);		
		return customerDB;
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		Customer customerDB = getCustomer(customer.getId());
		if(customerDB == null) {
			return null;
		}
		
		customerDB.setFirstName(customer.getFirstName());
		customerDB.setLastName(customer.getLastName());
		customerDB.setEmail(customer.getEmail());
		customerDB.setPhotoUrl(customer.getPhotoUrl());
		
		return customerRepository.save(customerDB);
	}

	@Override
	public Customer deleteCustomer(Customer customer) {
		
		Customer customerDB = getCustomer(customer.getId());
		if(customerDB == null) {
			return null;
		}
		
		customer.setState("DELETED");			
		return customerRepository.save(customer);
	}

	@Override
	public Customer getCustomer(Integer id) {
		
		return customerRepository.findById(id).orElse(null);
	}

}
