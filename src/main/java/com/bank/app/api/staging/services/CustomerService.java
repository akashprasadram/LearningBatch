package com.bank.app.api.staging.services;

import java.util.List;

import com.bank.app.api.staging.dto.CustomerDTO;
import com.bank.app.domain.staging.entities.StgCustomer;
import com.bank.app.domain.common.error.exceptions.DataCreationError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;

public interface CustomerService {
	public List<CustomerDTO> getCustomers();
	public CustomerDTO getCustomerById(Long id) throws DataNotFoundException;
	public CustomerDTO createCustomer(StgCustomer stgCustomer) throws DataCreationError;
	public List<CustomerDTO> createCustomers(List<StgCustomer> stgCustomers) throws DataCreationError;
}
