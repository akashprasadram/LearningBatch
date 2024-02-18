package com.bank.app.api.staging.controller;

import java.util.List;

import com.bank.app.api.handler.staging.StgAccountConverter;
import com.bank.app.api.staging.dto.StgAccountDTO;
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

import com.bank.app.domain.staging.entities.StgAccount;
import com.bank.app.domain.common.error.exceptions.DataIngestionError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;
import com.bank.app.api.staging.services.StgAccountService;

@RestController
@RequestMapping("/api/v1")
public class StgAccountController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(StgAccountController.class);
	
	private final StgAccountService stgAccountService;

	private final StgAccountConverter stgAccountConverter;


	public StgAccountController(StgAccountService stgAccountService, StgAccountConverter stgAccountConverter) {
		this.stgAccountService = stgAccountService;
		this.stgAccountConverter = stgAccountConverter;
	}

	@GetMapping("/account/")
	public ResponseEntity<List<StgAccountDTO>> getAllAccounts(){
		LOGGER.info("Inside getAllAccounts()");
		return ResponseEntity.ok(stgAccountService.getAccounts());
	}
	
	@GetMapping("/account/{id}")
	public ResponseEntity<StgAccountDTO> getAccountById(@PathVariable("id") Long id) throws DataNotFoundException {
		LOGGER.info("Inside getAccountById() with id : {}",id);
		return ResponseEntity.ok(stgAccountService.getAccountById(id));
	}
	
	@PostMapping("/accounts/")
	public ResponseEntity<List<StgAccountDTO>> saveAllAccounts(@RequestBody List<StgAccountDTO> stgAccountDTOS) throws DataIngestionError {
		LOGGER.info("Inside saveAllAccounts() with Accounts : {}", stgAccountDTOS);
		List<StgAccount> stgAccounts = stgAccountConverter.accountListConverter(stgAccountDTOS);
		return ResponseEntity.status(HttpStatus.CREATED).body(stgAccountService.createAccounts(stgAccounts));
	}
	
	@PostMapping("/account/")
	public ResponseEntity<StgAccountDTO> saveAccount(@RequestBody StgAccountDTO stgAccountDTO) throws DataIngestionError {
		LOGGER.info("Inside saveAccount() with Account : {}", stgAccountDTO);
		StgAccount stgAccount = stgAccountConverter.accountConverter(stgAccountDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(stgAccountService.createAccount(stgAccount));
	}
	
}
