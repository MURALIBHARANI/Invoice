package com.invoice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillingHeader {
	private String callerName;
	private Long billingId;
}
