package com.amegdev.store;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.amegdev.store.entity.Category;
import com.amegdev.store.entity.Product;
import com.amegdev.store.repository.IProductRepository;
import com.amegdev.store.service.IProductService;
import com.amegdev.store.service.ProductServiceImplements;

@SpringBootTest
public class ProductServiceMockTest {

	@Mock
	private IProductRepository productRepository;
	
	private IProductService productService;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		productService = new ProductServiceImplements(productRepository);
		
		Product p = Product.builder()
				.id(1L)
				.name("computer")
				.category(Category.builder().id(1L).build())
				.description("")
				.stock(Double.parseDouble("10"))
				.price(Double.parseDouble("1240.99"))
				.status("CREATED")
				.createAt(LocalDate.now())
				.build();
		
		Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(p));
		
		Mockito.when(productRepository.save(p)).thenReturn(p);
				
	}
	
	@Test
	public void whenValidId() {
		Product found = productService.get(1L);
		Assertions.assertThat(found.getName()).isEqualTo("computer");
	}
	
	@Test
	public void whenValidStock() {
		Product pro = productService.updateStock(1L, Double.parseDouble("8"));
		Assertions.assertThat(pro.getStock()).isEqualTo(18);
	}
	
	
}
