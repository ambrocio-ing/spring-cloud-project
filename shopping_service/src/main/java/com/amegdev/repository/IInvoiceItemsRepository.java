package com.amegdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amegdev.entity.InvoiceItem;

public interface IInvoiceItemsRepository extends JpaRepository<InvoiceItem, Integer> {

}
