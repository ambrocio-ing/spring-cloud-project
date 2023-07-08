package com.amegdev.service;

import java.util.List;

import com.amegdev.entity.Invoice;

public interface IInvoiceService {
	
	public List<Invoice> getInvoiceAll();
    public Invoice createInvoice(Invoice invoice);
    public Invoice updateInvoice(Invoice invoice);
    public Invoice deleteInvoice(Invoice invoice);
    public Invoice getInvoice(Integer id);
}
