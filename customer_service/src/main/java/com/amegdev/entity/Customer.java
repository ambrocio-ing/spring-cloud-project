package com.amegdev.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "customers")
public class Customer implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message = "El número de documento np puede ser vacío")
	@Size(min = 8, max = 8, message = "El tamaño del número de documento es 8")
	@Column(name = "number_id", unique = true, length = 8, nullable = false)
	private String numberId;
	
	@NotEmpty(message = "El nombre de puede ser vacío")
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@NotEmpty(message = "El apellido no puede ser vacío")
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@NotEmpty(message = "El correo no puede estar vacío")
	@Email(message = "No es una dirección de correo bien formada")
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(name = "photo_url")
	private String photoUrl;
	
	@NotNull(message = "La región no puede ser vacía")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Region region;
	
	private String state;
	
	private static final long serialVersionUID = 1L;	
}
