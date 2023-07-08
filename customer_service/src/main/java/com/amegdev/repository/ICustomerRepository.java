package com.amegdev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amegdev.entity.Customer;
import com.amegdev.entity.Region;

public interface ICustomerRepository extends JpaRepository<Customer, Integer> {
	
	public Customer findByNumberId(String numberId);
	public List<Customer> findByLastName(String lastName);
	public List<Customer> findByRegion(Region region);
}
