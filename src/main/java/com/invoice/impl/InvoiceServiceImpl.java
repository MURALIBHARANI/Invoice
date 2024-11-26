package com.invoice.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.invoice.entity.BillingHeader;
import com.invoice.model.InvoiceAckResponseDTO;
import com.invoice.model.InvoiceReq;
import com.invoice.model.InvoiceRes;
import com.invoice.process.InvoiceService;
import com.invoice.repository.BillingRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class InvoiceServiceImpl implements InvoiceService {
	@Autowired
	private BillingRepository billingRepository;

	@Autowired
	private RestTemplate restTemplate;

	@CircuitBreaker(name = "invoiceServiceImpl", fallbackMethod = "fallbackResponse")
	@Override
	public InvoiceRes process(InvoiceReq invoicereq) {
		ResponseEntity<InvoiceAckResponseDTO> ackResponse = restTemplate.exchange(
				"http://127.0.0.1:8080/InvoiceConformation/retriveInvoiceCode", HttpMethod.GET, null,
				InvoiceAckResponseDTO.class);
		InvoiceAckResponseDTO invoiceack = ackResponse.getBody();
		InvoiceRes invoiceRes = new InvoiceRes();
		if (invoiceack != null && invoiceack.getAckCode() != null && invoiceack.getAckCode().equals("INV200")) {
			Optional<BillingHeader> billing = billingRepository.findById(invoicereq.getBillingHeader().getBillingId());
			com.invoice.model.BillingHeader billingHeader = new com.invoice.model.BillingHeader();
			if (billing.isPresent()) {
				billingHeader.setBillingId(billing.get().getBillingId());
				billingHeader.setCallerName(billing.get().getCallerName());

			} else {
				billingHeader = invoicereq.getBillingHeader();
				BillingHeader billingEntityObj = new BillingHeader();
				billingEntityObj.setBillingId(billingHeader.getBillingId());
				billingEntityObj.setCallerName(billingHeader.getCallerName());
				billingRepository.save(billingEntityObj);
			}
			invoiceRes.setBillingHeader(billingHeader);
			return invoiceRes;
		} else
			return invoiceRes;
	}

}
