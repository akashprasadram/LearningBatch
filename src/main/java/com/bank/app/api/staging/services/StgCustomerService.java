package com.bank.app.api.staging.services;

import com.bank.app.api.staging.dto.StgCustomerDTO;
import com.bank.app.domain.common.error.exceptions.DataIngestionError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;
import com.bank.app.domain.staging.entities.StgCustomer;

import java.util.List;

public interface StgCustomerService {
	public List<StgCustomerDTO> getCustomers();
	public StgCustomerDTO getCustomerById(Long id) throws DataNotFoundException;
	public StgCustomerDTO createCustomer(StgCustomer stgCustomer) throws DataIngestionError;
	public List<StgCustomerDTO> createCustomers(List<StgCustomer> stgCustomers) throws DataIngestionError;
}
