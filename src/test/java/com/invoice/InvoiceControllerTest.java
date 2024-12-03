package com.invoice;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.invoice.model.BillingHeader;
import com.invoice.model.InvoiceReq;
import com.invoice.process.InvoiceService;

@SpringBootTest
class InvoiceControllerTest {

	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private RestTemplate restTemplate;

	private MockRestServiceServer mockServer;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	void createInvoiceTest() throws Exception {
		String expectedRes = "I/O error on GET request for \\\"http://127.0.0.1:8080/InvoiceConformation/retriveInvoiceCode\\\": Connection refused: connect";

		InvoiceReq invReq = new InvoiceReq();
		BillingHeader bhReq = new BillingHeader();
		bhReq.setBillingId(1);
		bhReq.setCallerName("Murali");
		invReq.setBillingHeader(bhReq);

		Assertions.assertEquals(invoiceService.process(invReq).getMessage(), expectedRes);
		mockServer.verify();
	}

}
