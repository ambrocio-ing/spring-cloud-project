package com.amegdev.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amegdev.store.entity.Category;
import com.amegdev.store.entity.Product;

public interface IProductRepository extends JpaRepository<Product, Long> {

	public List<Product> findByCategory(Category category);
	
}
