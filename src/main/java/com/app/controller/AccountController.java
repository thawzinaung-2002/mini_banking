package com.app.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.MoneyTransfer;
import com.app.model.Account;
import com.app.service.AccountService;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private final AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@GetMapping
	public List<Account> getAll() {
		return accountService.getAll();
	}
	
	@PostMapping
	public Account createAccount(@RequestBody Account account) {
		return accountService.createAccount(account);
	}
	
	@GetMapping("/{id}")
	public Optional<Account> getAccount(@PathVariable long id) {
		return accountService.getAccountById(id);
	}
	
	@PostMapping("/deposit/{id}")
	public Account deposit(@PathVariable long id, @RequestBody Account account) {
		return accountService.deposit(id, account.getAmount());
	}
	
	@PostMapping("/withdraw/{id}")
	public Account withdraw(@PathVariable long id, @RequestBody Account account) {
		return accountService.withdraw(id, account.getAmount());
	}
	
	@PostMapping("/transfer")
	public List<Account> transfer(@RequestBody MoneyTransfer transfer) {
		accountService.transfer(transfer.getIdSender(), transfer.getIdReceiver(), transfer.getAmount());
		return accountService.getAll();
	}
	
}
