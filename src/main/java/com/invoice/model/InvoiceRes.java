package com.invoice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceRes {
	private BillingHeader billingHeader;
	private String message;
}
