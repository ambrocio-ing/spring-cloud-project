package com.amegdev.store;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.amegdev.store.entity.Category;
import com.amegdev.store.entity.Product;
import com.amegdev.store.repository.ICategoryRepository;
import com.amegdev.store.repository.IProductRepository;

@DataJpaTest
public class ProductRepositoryMockTest {

	@Autowired
	private IProductRepository productRepository;
	
	@Autowired
	private ICategoryRepository categoryRepository;
	
	@Test
	public void  whenFIndByCategory() {
		//this.insertProducts();
		
		Product p = Product.builder()
				.id(4L)
				.name("computer")
				.category(Category.builder().id(1L).build())
				.description("")
				.stock(Double.parseDouble("10"))
				.price(Double.parseDouble("1240.99"))
				.status("CREATED")
				.createAt(LocalDate.now())
				.build();
		
		productRepository.save(p);
		
		List<Product> founds = productRepository.findByCategory(p.getCategory());
		
		Assertions.assertThat(founds.size()).isEqualTo(3);
	}	
	
	public void insertProducts() {
		List<Category> cats = Arrays.asList(new Category(1L, "adidas"), new Category(2L, "books"), new Category(3L, "electronics"));
		categoryRepository.saveAll(cats);
		
		List<Product> prods = Arrays.asList(
				new Product(1L, "adidas Cloudfoam Ultimate","Walk in the air in the black / black CLOUDFOAM ULTIMATE running shoe from ADIDAS", 5.00, 178.89, "CREATED", LocalDate.now(), cats.get(0)),
				new Product(2L, "under armour Mens Micro G Assert – 7", "under armour Men ''Lightweight mesh upper delivers complete breathability . Durable leather overlays for stability", 4.00, 12.5, "CREATED",LocalDate.now(),cats.get(0)),
				new Product(3L, "Spring Boot in Action", "under armour Men '' Craig Walls is a software developer at Pivotal and is the author of Spring in Action", 12.00, 40.06, "CREATED", LocalDate.now(), cats.get(1)));
		
		productRepository.saveAll(prods);
	}
	
}
