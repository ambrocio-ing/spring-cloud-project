package com.amegdev.store.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "El nombre no debe ser vacío")
	private String name;
	
	private String description;
	
	@Positive(message = "El stock debe ser mayor a cero")
	private Double stock;
	
	private Double price;
	
	private String status;
	
	@Column(name = "create_at")
	private LocalDate createAt;
	
	@NotNull(message = "La categoría no debe ser vacía")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Category category;	
	
	@PrePersist
	public void persist() {
		this.createAt = LocalDate.now();
		this.status = "CREATED";
	}

}
