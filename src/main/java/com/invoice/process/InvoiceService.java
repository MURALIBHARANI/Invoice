package com.invoice.process;

import com.invoice.model.InvoiceReq;
import com.invoice.model.InvoiceRes;

public interface InvoiceService {
	InvoiceRes process(InvoiceReq invoicereq);
}
