package com.bank.app.api.staging.services;

import com.bank.app.api.handler.staging.StgCustomerConverter;
import com.bank.app.api.staging.dto.StgCustomerDTO;
import com.bank.app.domain.common.error.exceptions.DataIngestionError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;
import com.bank.app.domain.staging.entities.StgCustomer;
import com.bank.app.domain.staging.repository.StgCustomerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StgCustomerServiceImpl implements StgCustomerService {

	private static final Logger LOGGER= LoggerFactory.getLogger(StgCustomerServiceImpl.class);
	private final StgCustomerRepo stgCustomerRepo;

	private final StgCustomerConverter stgCustomerConverter;

	public StgCustomerServiceImpl(StgCustomerRepo stgCustomerRepo, StgCustomerConverter stgCustomerConverter) {
		this.stgCustomerRepo = stgCustomerRepo;
		this.stgCustomerConverter = stgCustomerConverter;
	}

	@Override
	public List<StgCustomerDTO> getCustomers() {
		LOGGER.info("Inside getCustomers()");
		List<StgCustomer> stgCustomers = stgCustomerRepo.findAll();
		return stgCustomerConverter.customerDTOListConverter(stgCustomers);
	}

	@Override
	public StgCustomerDTO getCustomerById(Long id) throws DataNotFoundException {

		LOGGER.info("Inside getCustomerById() with id : {}",id);
		Optional<StgCustomer> customer = stgCustomerRepo.findById(id);
		if(customer.isEmpty()) {
			throw new DataNotFoundException("Customer not found with id : "+id);
		}
		StgCustomer resultStgCustomer = customer.get();

		return stgCustomerConverter.customerDTOConverter(resultStgCustomer);
	}

	@Override
	public StgCustomerDTO createCustomer(StgCustomer stgCustomer) throws DataIngestionError {
		LOGGER.info("Inside createCustomer() with StgCustomer : {}", stgCustomer);
		StgCustomer savedStgCustomer = stgCustomerRepo.save(stgCustomer);
		if(savedStgCustomer.getCustomerId()==0) {
			throw new DataIngestionError("Unable to create Customer");
		}
		return stgCustomerConverter.customerDTOConverter(savedStgCustomer);
	}

	@Override
	public List<StgCustomerDTO> createCustomers(List<StgCustomer> stgCustomers) throws DataIngestionError {
		LOGGER.info("Inside createCustomers() with StgCustomers : {}", stgCustomers);
		List<StgCustomer> savedStgCustomers = stgCustomerRepo.saveAll(stgCustomers);
		if(savedStgCustomers.isEmpty()) {
			throw new DataIngestionError("Unable to create customers");
		}
		return stgCustomerConverter.customerDTOListConverter(savedStgCustomers);
	}

}
