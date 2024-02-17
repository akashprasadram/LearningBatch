package com.bank.app.api.staging.services;

import java.util.List;

import com.bank.app.api.staging.dto.StgCustomerDTO;
import com.bank.app.domain.staging.entities.StgCustomer;
import com.bank.app.domain.common.error.exceptions.DataCreationError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;

public interface StgCustomerService {
	public List<StgCustomerDTO> getCustomers();
	public StgCustomerDTO getCustomerById(Long id) throws DataNotFoundException;
	public StgCustomerDTO createCustomer(StgCustomer stgCustomer) throws DataCreationError;
	public List<StgCustomerDTO> createCustomers(List<StgCustomer> stgCustomers) throws DataCreationError;
}
