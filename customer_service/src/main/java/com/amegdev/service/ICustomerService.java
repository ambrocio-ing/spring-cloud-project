package com.amegdev.service;

import java.util.List;

import com.amegdev.entity.Customer;
import com.amegdev.entity.Region;

public interface ICustomerService {
	List<Customer> getCustomerAll();
	List<Customer> getCustomerByRegion(Region region);
	Customer createCustomer(Customer customer);
	Customer updateCustomer(Customer customer);
	Customer deleteCustomer(Customer customer);
	Customer getCustomer(Integer id);
}
