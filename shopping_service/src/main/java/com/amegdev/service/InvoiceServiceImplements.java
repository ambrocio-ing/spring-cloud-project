package com.amegdev.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amegdev.client.ICustomerClient;
import com.amegdev.client.IProductClient;
import com.amegdev.entity.Invoice;
import com.amegdev.entity.InvoiceItem;
import com.amegdev.model.Customer;
import com.amegdev.model.Product;
//import com.amegdev.repository.IInvoiceItemsRepository;
import com.amegdev.repository.IInvoiceRepository;

//import lombok.extern.slf4j.Slf4j;

//@Slf4j
@Service
public class InvoiceServiceImplements implements IInvoiceService {
	
	@Autowired
    private IInvoiceRepository invoiceRepository;

//    @Autowired
//    private IInvoiceItemsRepository invoiceItemsRepository;

    @Autowired
    private ICustomerClient customerClient;
    
    @Autowired
    private IProductClient productClient;
	
	@Override
	public List<Invoice> getInvoiceAll() {
		
		return invoiceRepository.findAll();
	}

	@Override
	public Invoice createInvoice(Invoice invoice) {
		Invoice invoiceDB = invoiceRepository.findByNumberInvoice(invoice.getNumberInvoice()).orElse(null);
        if (invoiceDB !=null){
            return  invoiceDB;
        }
        
        invoice.setState("CREATED");        
        invoiceDB = invoiceRepository.save(invoice);      
        invoiceDB.getItems().forEach(invoiceItem -> {
        	productClient.updateStockProduct(invoiceItem.getId(), invoiceItem.getQuantity() * -1);
        });
        
        return invoiceDB;
	}

	@Override
	public Invoice updateInvoice(Invoice invoice) {
		
		Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());
        
        return invoiceRepository.save(invoiceDB);
	}

	@Override
	public Invoice deleteInvoice(Invoice invoice) {
		Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        
        invoiceDB.setState("DELETED");
        
        return invoiceRepository.save(invoiceDB);
	}

	@Override
	public Invoice getInvoice(Integer id) {		
		Invoice invoice = invoiceRepository.findById(id).orElse(null);
		
		if(invoice != null) {
			Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
			invoice.setCustomer(customer);
			List<InvoiceItem> listaItems = invoice.getItems()
					.stream()
					.map(inItem -> {
						Product product = productClient.getProduct(inItem.getProductId()).getBody();
						inItem.setProduct(product);
						return inItem;						
					})
					.collect(Collectors.toList());
			invoice.setItems(listaItems);
		}
		
		return invoice;
	}

}
