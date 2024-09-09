package com.app.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.model.Account;
import com.app.repository.AccountRepository;

@Service
public class AccountService {

	private final AccountRepository accountRepository;
	
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	public Account createAccount(Account account) {
		return this.accountRepository.save(account);
	}
	
	public Optional<Account> getAccountById(long id) {
		return this.accountRepository.findById(id);
	}
	
	public List<Account> getAll() {
		return (List<Account>) this.accountRepository.findAll();
	}
	
	public Account deposit(Long id, BigDecimal amount) {
		Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
		account.setAmount(account.getAmount().add(amount));
		return accountRepository.save(account);
	}
	
	public Account withdraw(Long id, BigDecimal amount) {
		Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
		if(account.getAmount().compareTo(amount) == -1) {
			throw new RuntimeException("Insuffient funds");
		}
		account.setAmount(account.getAmount().subtract(amount));
		return accountRepository.save(account);
	}
	
	@Transactional
	public void transfer(long idSender, long idReceiver, BigDecimal amount) {
		Account senderAccount = accountRepository.findById(idSender).orElseThrow(() -> new RuntimeException("Account not found"));
		if(senderAccount.getAmount().compareTo(amount) == -1) {
			throw new RuntimeException("Insuffient amount");
		}
		Account receiverAccount = accountRepository.findById(idReceiver).orElseThrow(() -> new RuntimeException("Account not found"));
		senderAccount.setAmount(senderAccount.getAmount().subtract(amount));
		receiverAccount.setAmount(receiverAccount.getAmount().add(amount));
		accountRepository.save(senderAccount);
		accountRepository.save(receiverAccount);
	}
}
