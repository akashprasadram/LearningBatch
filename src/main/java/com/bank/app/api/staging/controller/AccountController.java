package com.bank.app.api.staging.controller;

import java.util.List;

import com.bank.app.api.handler.staging.AccountConverter;
import com.bank.app.api.staging.dto.AccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.app.domain.staging.entities.Account;
import com.bank.app.domain.common.error.exceptions.DataCreationError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;
import com.bank.app.api.staging.services.AccountService;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(AccountController.class);
	
	private final AccountService accountService;

	private final AccountConverter accountConverter;


	public AccountController(AccountService accountService, AccountConverter accountConverter) {
		this.accountService = accountService;
		this.accountConverter = accountConverter;
	}

	@GetMapping("/account/")
	public ResponseEntity<List<AccountDTO>> getAllAccounts(){
		LOGGER.info("Inside getAllAccounts()");
		return ResponseEntity.ok(accountService.getAccounts());
	}
	
	@GetMapping("/account/{id}")
	public ResponseEntity<AccountDTO> getAccountById(@PathVariable("id") Long id) throws DataNotFoundException{
		LOGGER.info("Inside getAccountById() with id : {}",id);
		return ResponseEntity.ok(accountService.getAccountById(id));
	}
	
	@PostMapping("/accounts/")
	public ResponseEntity<List<AccountDTO>> saveAllAccounts(@RequestBody List<AccountDTO> accountDTOs) throws DataCreationError{
		LOGGER.info("Inside saveAllAccounts() with Accounts : {}",accountDTOs);
		List<Account> accounts=accountConverter.accountListConverter(accountDTOs);
		return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccounts(accounts));
	}
	
	@PostMapping("/account/")
	public ResponseEntity<AccountDTO> saveAccount(@RequestBody AccountDTO accountDTO) throws DataCreationError{
		LOGGER.info("Inside saveAccount() with Account : {}",accountDTO);
		Account account=accountConverter.accountConverter(accountDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(account));
	}
	
}
