package com.bank.app.api.staging.services;

import java.util.List;
import java.util.Optional;

import com.bank.app.api.handler.staging.AccountConverter;
import com.bank.app.api.staging.dto.AccountDTO;
import com.bank.app.domain.staging.entities.StgAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
		List<StgAccount> stgAccounts = accountRepo.findAll();
		return accountConverter.accountDTOListConverter(stgAccounts);
	}

	@Override
	public AccountDTO getAccountById(Long id) throws DataNotFoundException {
		LOGGER.info("Inside getAccountById() with id : {}",id);
		Optional<StgAccount> account = accountRepo.findById(id);
		if(account.isEmpty()) {
			throw new DataNotFoundException("Account data not found with Id : "+id);
		}
		else {
			StgAccount resultStgAccount = account.get();
			return accountConverter.accountDTOConverter(resultStgAccount);
		}
	}

	@Override
	public AccountDTO createAccount(StgAccount stgAccount) throws DataCreationError{
		LOGGER.info("Inside createAccount() with Account : {}", stgAccount);
		StgAccount savedStgAccount = accountRepo.save(stgAccount);
		if(savedStgAccount.getAId()==0) {
			throw new DataCreationError("Unable to create account");
		}
		return accountConverter.accountDTOConverter(savedStgAccount);
	}

	@Override
	public List<AccountDTO> createAccounts(List<StgAccount> stgAccounts) throws DataCreationError{
		LOGGER.info("Inside createAccounts() with Accounts : {}", stgAccounts);
		List<StgAccount> savedStgAccounts =accountRepo.saveAll(stgAccounts);
		if(savedStgAccounts.isEmpty()) {
			throw new DataCreationError("Unable to create accounts");
		}
		return accountConverter.accountDTOListConverter(savedStgAccounts);
	}

}
