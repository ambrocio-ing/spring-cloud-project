package com.amegdev.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amegdev.entity.Invoice;

public interface IInvoiceRepository extends JpaRepository<Invoice, Integer> {
	public List<Invoice> findByCustomerId(Integer customerId);
	public Optional<Invoice> findByNumberInvoice(String numberInvoice);
}
