package com.bank.app.api.staging.services;

import java.util.List;
import java.util.Optional;

import com.bank.app.api.handler.staging.CustomerConverter;
import com.bank.app.api.staging.dto.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bank.app.domain.staging.entities.Customer;
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
		List<Customer> customers = customerRepo.findAll();
		return customerConverter.customerDTOListConverter(customers);
	}

	@Override
	public CustomerDTO getCustomerById(Long id) throws DataNotFoundException {

		LOGGER.info("Inside getCustomerById() with id : {}",id);
		Optional<Customer> customer = customerRepo.findById(id);
		if(customer.isEmpty()) {
			throw new DataNotFoundException("Customer not found with id : "+id);
		}
		Customer resultCustomer = customer.get();

		return customerConverter.customerDTOConverter(resultCustomer);
	}

	@Override
	public CustomerDTO createCustomer(Customer customer) throws DataCreationError {
		LOGGER.info("Inside createCustomer() with Customer : {}",customer);
		Customer savedCustomer = customerRepo.save(customer);
		if(savedCustomer.getCId()==0) {
			throw new DataCreationError("Unable to create Customer");
		}
		return customerConverter.customerDTOConverter(savedCustomer);
	}

	@Override
	public List<CustomerDTO> createCustomers(List<Customer> customers) throws DataCreationError {
		LOGGER.info("Inside createCustomers() with Customers : {}",customers);
		List<Customer> savedCustomers=customerRepo.saveAll(customers);
		if(savedCustomers.isEmpty()) {
			throw new DataCreationError("Unable to create customers");
		}
		return customerConverter.customerDTOListConverter(savedCustomers);
	}

}
