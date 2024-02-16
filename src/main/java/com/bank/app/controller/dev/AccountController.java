package com.bank.app.controller.dev;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.app.entities.dev.Account;
import com.bank.app.exceptions.DataCreationError;
import com.bank.app.exceptions.DataNotFoundException;
import com.bank.app.services.dev.AccountService;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(AccountController.class);
	
	private AccountService accountService;
	

	public AccountController(AccountService accountService) {
		super();
		this.accountService = accountService;
	}

	@GetMapping("/account/")
	public ResponseEntity<List<Account>> getAllAccounts(){
		return ResponseEntity.ok(accountService.getAccounts());
	}
	
	@GetMapping("/account/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable("id") Long id) throws DataNotFoundException{
		return ResponseEntity.ok(accountService.getAccountById(id));
	}
	
	@PostMapping("/accounts/")
	public ResponseEntity<List<Account>> saveAllAccounts(@RequestBody List<Account> accounts) throws DataCreationError{
		return ResponseEntity.ok(accountService.createAccounts(accounts));
	}
	
	@PostMapping("/account/")
	public ResponseEntity<Account> saveAccount(@RequestBody Account account) throws DataCreationError{
		LOGGER.info("Inside saveAccount() with account : {}",account);
		return ResponseEntity.ok(accountService.createAccount(account));
	}
	
}
