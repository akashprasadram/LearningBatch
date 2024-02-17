package com.bank.app.api.staging.services;

import java.util.List;
import java.util.Optional;

import com.bank.app.api.handler.staging.CustomerConverter;
import com.bank.app.api.staging.dto.CustomerDTO;
import com.bank.app.domain.staging.entities.StgCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bank.app.domain.common.error.exceptions.DataCreationError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;
import com.bank.app.domain.staging.repository.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger LOGGER= LoggerFactory.getLogger(CustomerServiceImpl.class);
	private final CustomerRepo customerRepo;

	private final CustomerConverter customerConverter;

	public CustomerServiceImpl(CustomerRepo customerRepo, CustomerConverter customerConverter) {
		this.customerRepo = customerRepo;
		this.customerConverter = customerConverter;
	}

	@Override
	public List<CustomerDTO> getCustomers() {
		LOGGER.info("Inside getCustomers()");
		List<StgCustomer> stgCustomers = customerRepo.findAll();
		return customerConverter.customerDTOListConverter(stgCustomers);
	}

	@Override
	public CustomerDTO getCustomerById(Long id) throws DataNotFoundException {

		LOGGER.info("Inside getCustomerById() with id : {}",id);
		Optional<StgCustomer> customer = customerRepo.findById(id);
		if(customer.isEmpty()) {
			throw new DataNotFoundException("Customer not found with id : "+id);
		}
		StgCustomer resultStgCustomer = customer.get();

		return customerConverter.customerDTOConverter(resultStgCustomer);
	}

	@Override
	public CustomerDTO createCustomer(StgCustomer stgCustomer) throws DataCreationError {
		LOGGER.info("Inside createCustomer() with Customer : {}", stgCustomer);
		StgCustomer savedStgCustomer = customerRepo.save(stgCustomer);
		if(savedStgCustomer.getCustId()==0) {
			throw new DataCreationError("Unable to create Customer");
		}
		return customerConverter.customerDTOConverter(savedStgCustomer);
	}

	@Override
	public List<CustomerDTO> createCustomers(List<StgCustomer> stgCustomers) throws DataCreationError {
		LOGGER.info("Inside createCustomers() with Customers : {}", stgCustomers);
		List<StgCustomer> savedStgCustomers =customerRepo.saveAll(stgCustomers);
		if(savedStgCustomers.isEmpty()) {
			throw new DataCreationError("Unable to create customers");
		}
		return customerConverter.customerDTOListConverter(savedStgCustomers);
	}

}
