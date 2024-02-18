package com.bank.app.api.staging.services;

import java.util.List;
import java.util.Optional;

import com.bank.app.api.handler.staging.StgAccountConverter;
import com.bank.app.api.staging.dto.StgAccountDTO;
import com.bank.app.domain.staging.entities.StgAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bank.app.domain.common.error.exceptions.DataIngestionError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;
import com.bank.app.domain.staging.repository.StgAccountRepo;

@Service
public class StgAccountServiceImpl implements StgAccountService {

	private static final Logger LOGGER= LoggerFactory.getLogger(StgAccountServiceImpl.class);
	private final StgAccountRepo stgAccountRepo;

	private final StgAccountConverter stgAccountConverter;


	public StgAccountServiceImpl(StgAccountRepo stgAccountRepo, StgAccountConverter stgAccountConverter) {
		this.stgAccountRepo = stgAccountRepo;
		this.stgAccountConverter = stgAccountConverter;
	}

	@Override
	public List<StgAccountDTO> getAccounts() {
		LOGGER.info("Inside getAccounts()");
		List<StgAccount> stgAccounts = stgAccountRepo.findAll();
		return stgAccountConverter.accountDTOListConverter(stgAccounts);
	}

	@Override
	public StgAccountDTO getAccountById(Long id) throws DataNotFoundException {
		LOGGER.info("Inside getAccountById() with id : {}",id);
		Optional<StgAccount> account = stgAccountRepo.findById(id);
		if(account.isEmpty()) {
			throw new DataNotFoundException("Account data not found with Id : "+id);
		}
		else {
			StgAccount resultStgAccount = account.get();
			return stgAccountConverter.accountDTOConverter(resultStgAccount);
		}
	}

	@Override
	public StgAccountDTO createAccount(StgAccount stgAccount) throws DataIngestionError {
		LOGGER.info("Inside createAccount() with StgAccount : {}", stgAccount);
		StgAccount savedStgAccount = stgAccountRepo.save(stgAccount);
		if(savedStgAccount.getAccountId()==0) {
			throw new DataIngestionError("Unable to create account");
		}
		return stgAccountConverter.accountDTOConverter(savedStgAccount);
	}

	@Override
	public List<StgAccountDTO> createAccounts(List<StgAccount> stgAccounts) throws DataIngestionError {
		LOGGER.info("Inside createAccounts() with StgAccounts : {}", stgAccounts);
		List<StgAccount> savedStgAccounts = stgAccountRepo.saveAll(stgAccounts);
		if(savedStgAccounts.isEmpty()) {
			throw new DataIngestionError("Unable to create accounts");
		}
		return stgAccountConverter.accountDTOListConverter(savedStgAccounts);
	}

}
