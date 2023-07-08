package com.amegdev.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "invoice_items")
public class InvoiceItem implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Positive(message = "El stock debe ser mayor que cero")
	private Double quantity;
	
	private Double price;
	
	@Column(name = "product_id")
	private Integer productId;
	
	@Transient
	private Double subTotal;
	
	public Double getSubTotal() {
		if(this.price > 0 && this.quantity > 0) {
			return this.price * this.quantity;
		}
		else {
			return (double)0;
		}
	}
	
	private static final long serialVersionUID = 1L;
}
