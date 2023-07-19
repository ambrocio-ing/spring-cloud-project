package com.amegdev.store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amegdev.store.entity.Category;
import com.amegdev.store.entity.Product;
import com.amegdev.store.repository.IProductRepository;

import lombok.RequiredArgsConstructor;

@Service
//@RequiredArgsConstructor
public class ProductServiceImplements implements IProductService {

	//@Autowired
	private final IProductRepository productRepository;
	
	public ProductServiceImplements(IProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	public List<Product> getAll() {
		
		return productRepository.findAll();
	}

	@Override
	public Product create(Product entity) {
		
		return productRepository.save(entity);
	}

	@Override
	public Product update(Product entity) {
		productRepository.save(entity);
		Product pro = get(entity.getId());
		return pro != null ? pro : null;
	}

	@Override
	public Product delete(Integer id) {
		
		Product pro = productRepository.findById(id).orElse(null);
		if(pro == null)
			return null;
		
		pro.setStatus("DELETED");
		return productRepository.save(pro);
	}

	@Override
	public Product get(Integer id) {
		
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public List<Product> getByCategory(Category category) {
		
		return productRepository.findByCategory(category);
	}

	@Override
	public Product updateStock(Integer id, Double quantity) {
		Product pro = productRepository.findById(id).orElse(null);
		
		if(pro != null) {
			pro.setStock(pro.getStock() + quantity);
			productRepository.save(pro);
		}		
		
		return pro;
	}

}
