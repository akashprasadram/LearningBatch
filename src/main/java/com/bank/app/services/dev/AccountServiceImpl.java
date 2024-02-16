package com.bank.app.services.dev;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bank.app.entities.dev.Account;
import com.bank.app.exceptions.DataCreationError;
import com.bank.app.exceptions.DataNotFoundException;
import com.bank.app.repo.dev.AccountRepo;

@Service
public class AccountServiceImpl implements AccountService {
	
	private AccountRepo accountRepo;
	

	public AccountServiceImpl(AccountRepo accountRepo) {
		super();
		this.accountRepo = accountRepo;
	}

	@Override
	public List<Account> getAccounts() {
		return accountRepo.findAll();
	}

	@Override
	public Account getAccountById(Long id) throws DataNotFoundException {
		Optional<Account> account = accountRepo.findById(id);
		if(account.isEmpty()) {
			throw new DataNotFoundException("Account data not found with Id : "+id);
		}
		else {
			return account.get();
		}
	}

	@Override
	public Account createAccount(Account account) throws DataCreationError{
		Account savedAccount = accountRepo.save(account);
		if(savedAccount.getAId()==0) {
			throw new DataCreationError("Unable to create account");
		}
		return savedAccount;
	}

	@Override
	public List<Account> createAccounts(List<Account> accounts) throws DataCreationError{
		List<Account> savedAccounts=accountRepo.saveAll(accounts);
		if(savedAccounts.isEmpty()) {
			throw new DataCreationError("Unable to create accounts");
		}
		return savedAccounts;
	}

}
