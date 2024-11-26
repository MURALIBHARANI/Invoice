package com.invoice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoice.model.InvoiceReq;
import com.invoice.model.InvoiceRes;
import com.invoice.process.InvoiceService;

@RestController
@RequestMapping(value = "/InvoiceApp")
public class InvoiceController {
	@Autowired
	private InvoiceService invoiceService;

	@PostMapping("/CreateInvoice")
	private ResponseEntity<InvoiceRes> creatingInvoice(@RequestBody InvoiceReq invoice) {
		InvoiceRes invoiceRes = invoiceService.process(invoice);
		return new ResponseEntity<>(invoiceRes, HttpStatus.CREATED);
	}
}
