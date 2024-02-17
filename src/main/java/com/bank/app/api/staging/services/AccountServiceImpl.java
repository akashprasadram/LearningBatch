package com.bank.app.api.staging.services;

import java.util.List;
import java.util.Optional;

import com.bank.app.api.handler.staging.AccountConverter;
import com.bank.app.api.staging.controller.RelationshipController;
import com.bank.app.api.staging.dto.AccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bank.app.domain.staging.entities.Account;
import com.bank.app.domain.common.error.exceptions.DataCreationError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;
import com.bank.app.domain.staging.repository.AccountRepo;

@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger LOGGER= LoggerFactory.getLogger(AccountServiceImpl.class);
	private final AccountRepo accountRepo;

	private final AccountConverter accountConverter;


	public AccountServiceImpl(AccountRepo accountRepo, AccountConverter accountConverter) {
		this.accountRepo = accountRepo;
		this.accountConverter = accountConverter;
	}

	@Override
	public List<AccountDTO> getAccounts() {
		LOGGER.info("Inside getAccounts()");
		List<Account> accounts = accountRepo.findAll();
		return accountConverter.accountDTOListConverter(accounts);
	}

	@Override
	public AccountDTO getAccountById(Long id) throws DataNotFoundException {
		LOGGER.info("Inside getAccountById() with id : {}",id);
		Optional<Account> account = accountRepo.findById(id);
		if(account.isEmpty()) {
			throw new DataNotFoundException("Account data not found with Id : "+id);
		}
		else {
			Account resultAccount = account.get();
			return accountConverter.accountDTOConverter(resultAccount);
		}
	}

	@Override
	public AccountDTO createAccount(Account account) throws DataCreationError{
		LOGGER.info("Inside createAccount() with Account : {}",account);
		Account savedAccount = accountRepo.save(account);
		if(savedAccount.getAId()==0) {
			throw new DataCreationError("Unable to create account");
		}
		return accountConverter.accountDTOConverter(savedAccount);
	}

	@Override
	public List<AccountDTO> createAccounts(List<Account> accounts) throws DataCreationError{
		LOGGER.info("Inside createAccounts() with Accounts : {}",accounts);
		List<Account> savedAccounts=accountRepo.saveAll(accounts);
		if(savedAccounts.isEmpty()) {
			throw new DataCreationError("Unable to create accounts");
		}
		return accountConverter.accountDTOListConverter(savedAccounts);
	}

}
