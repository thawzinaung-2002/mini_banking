package com.app.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MoneyTransfer {

	private long idSender;
	private long idReceiver;
	private BigDecimal amount;
	
}
