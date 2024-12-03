package com.invoice.web;

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
	private final InvoiceService invoiceService;

	InvoiceController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@PostMapping("/CreateInvoice")
	public ResponseEntity<InvoiceRes> creatingInvoice(@RequestBody InvoiceReq invoice) throws Exception {
		InvoiceRes invoiceRes = invoiceService.process(invoice);
		return new ResponseEntity<>(invoiceRes, HttpStatus.CREATED);
	}
}
