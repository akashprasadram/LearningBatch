package com.bank.app.services.dev;

import java.util.List;

import com.bank.app.entities.dev.Customer;
import com.bank.app.exceptions.DataCreationError;
import com.bank.app.exceptions.DataNotFoundException;

public interface CustomerService {
	public List<Customer> getCustomers();
	public Customer getCustomerById(Long id) throws DataNotFoundException;
	public Customer createAccount(Customer customer) throws DataCreationError;
	public List<Customer> createAccounts(List<Customer> customers) throws DataCreationError;
}
