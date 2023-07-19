package com.amegdev.store.service;

import java.util.List;

import com.amegdev.store.entity.Category;
import com.amegdev.store.entity.Product;

public interface IProductService extends ICrudService<Product>{
	
	public List<Product> getByCategory(Category category);
	public Product updateStock(Integer id, Double quantity);		
}
