package com.bank.app.services.dev;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bank.app.entities.dev.Customer;
import com.bank.app.exceptions.DataCreationError;
import com.bank.app.exceptions.DataNotFoundException;
import com.bank.app.repo.dev.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepo customerRepo;

	public CustomerServiceImpl(CustomerRepo customerRepo) {
		this.customerRepo = customerRepo;
	}

	@Override
	public List<Customer> getCustomers() {
		return customerRepo.findAll();
	}

	@Override
	public Customer getCustomerById(Long id) throws DataNotFoundException {
		
		Optional<Customer> customer = customerRepo.findById(id);
		if(customer.isEmpty()) {
			throw new DataNotFoundException("Customer not found with id : "+id);
		}
		return customer.get();
	}

	@Override
	public Customer createAccount(Customer customer) throws DataCreationError {
		Customer savedCustomer = customerRepo.save(customer);
		if(savedCustomer.getcId()==0) {
			throw new DataCreationError("Unable to create Customer");
		}
		return savedCustomer;
	}

	@Override
	public List<Customer> createAccounts(List<Customer> customers) throws DataCreationError {
		List<Customer> savedCustomers=customerRepo.saveAll(customers);
		if(savedCustomers.isEmpty()) {
			throw new DataCreationError("Unable to create customers");
		}
		return savedCustomers;
	}

}
