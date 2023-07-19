package com.amegdev.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.amegdev.entity.Invoice;
import com.amegdev.service.IInvoiceService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/invoices")
public class InvoiceController {
	
	 @Autowired
	 private IInvoiceService invoiceService;
	
	 @GetMapping
	 public ResponseEntity<List<Invoice>> listAllInvoices() {
		 List<Invoice> invoices = invoiceService.getInvoiceAll();
	     if (invoices.isEmpty()) {
	         return  ResponseEntity.noContent().build();
	     }
	     
	     return  ResponseEntity.ok(invoices);	  
	 }
	   
	    @GetMapping(value = "/{id}")
	    public ResponseEntity<Invoice> getInvoice(@PathVariable("id") Integer id) {
	        log.info("Fetching Invoice with id {}", id);
	        Invoice invoice  = invoiceService.getInvoice(id);
	        if (null == invoice) {
	            log.error("Invoice with id {} not found.", id);
	            return  ResponseEntity.notFound().build();
	        }
	        
	        return  ResponseEntity.ok(invoice);
	    }
	 
	    @PostMapping
	    public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody Invoice invoice, BindingResult result) {
	        log.info("Creating Invoice : {}", invoice);
	        if (result.hasErrors()){
	        	String errorMessage = result.getFieldErrors().stream()
	        			.map(err -> String.format("Error en: %s : %s", err.getField(), err.getDefaultMessage()))
	        			.collect(Collectors.joining(" ; "));
	        			
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
	        }
	        
	        Invoice invoiceDB = invoiceService.createInvoice (invoice);

	        return  ResponseEntity.status( HttpStatus.CREATED).body(invoiceDB);
	    }
	   
	    @PutMapping(value = "/{id}")
	    public ResponseEntity<?> updateInvoice(@PathVariable("id") Integer id, @RequestBody Invoice invoice) {
	        log.info("Updating Invoice with id {}", id);

	        invoice.setId(id);
	        Invoice currentInvoice=invoiceService.updateInvoice(invoice);

	        if (currentInvoice == null) {
	            log.error("Unable to update. Invoice with id {} not found.", id);
	            return  ResponseEntity.notFound().build();
	        }
	        
	        return  ResponseEntity.ok(currentInvoice);
	    }

	    
	    @DeleteMapping(value = "/{id}")
	    public ResponseEntity<Invoice> deleteInvoice(@PathVariable("id") Integer id) {
	        log.info("Fetching & Deleting Invoice with id {}", id);

	        Invoice invoice = invoiceService.getInvoice(id);
	        if (invoice == null) {
	            log.error("Unable to delete. Invoice with id {} not found.", id);
	            return  ResponseEntity.notFound().build();
	        }
	        
	        invoice = invoiceService.deleteInvoice(invoice);	        
	        return ResponseEntity.ok(invoice);
	    }   
	   	
}
