package com.invoice.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.invoice.config.ResourceNotFoundException;
import com.invoice.entity.BillingHeader;
import com.invoice.model.InvoiceAckResponseDTO;
import com.invoice.model.InvoiceReq;
import com.invoice.model.InvoiceRes;
import com.invoice.process.InvoiceService;
import com.invoice.repository.BillingRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class InvoiceServiceImpl implements InvoiceService {
	private static final String INVOICE_CONFORMATION_URL = "http://127.0.0.1:8080/InvoiceConformation/retriveInvoiceCode";

	@Autowired
	private BillingRepository billingRepository;

	@Autowired
	private RestTemplate restTemplate;

	@CircuitBreaker(name = "invoiceServiceImpl", fallbackMethod = "fallbackResponse")
	@Override
	public InvoiceRes process(InvoiceReq invoicereq) {
		InvoiceRes invoiceRes = new InvoiceRes();

		ResponseEntity<InvoiceAckResponseDTO> ackResponse = restTemplate.exchange(INVOICE_CONFORMATION_URL,
				HttpMethod.GET, null, InvoiceAckResponseDTO.class);
		InvoiceAckResponseDTO invoiceack = ackResponse.getBody();
		if (invoiceack != null && invoiceack.getAckCode() != null && invoiceack.getAckCode().equals("INV200")) {
			Optional<BillingHeader> billing = billingRepository.findById(invoicereq.getBillingHeader().getBillingId());
			com.invoice.model.BillingHeader billingHeader = new com.invoice.model.BillingHeader();
			if (billing.isPresent()) {
				billingHeader.setCallerName(billing.get().getCallerName());
				invoiceRes.setMessage("Invoice Updated Successfully");
			} else {
				billingHeader = invoicereq.getBillingHeader();
				BillingHeader billingEntityObj = new BillingHeader();
				billingEntityObj.setBillingId(billingHeader.getBillingId());
				billingEntityObj.setCallerName(billingHeader.getCallerName());
				billingEntityObj.setInvoiceCode(invoiceack.getAckCode());
				billingRepository.save(billingEntityObj);
				invoiceRes.setMessage("Invoice Created Successfully");
			}
			invoiceRes.setBillingHeader(billingHeader);
			return invoiceRes;
		} else {
			throw new ResourceNotFoundException("Invaild Invoice Found");
		}
	}

	/**
	 * Fallback method called when the circuit breaker is open.
	 * 
	 * @param ex Exception thrown
	 * @return Fallback response
	 */
	public String fallbackResponse(Exception ex) {
		return "Fallback response: " + ex.getMessage();
	}

}
