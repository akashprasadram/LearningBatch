package com.bank.app.api.handler.staging;

import com.bank.app.api.staging.dto.CustomerDTO;
import com.bank.app.domain.staging.entities.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerConverter {

    private static final Logger LOGGER= LoggerFactory.getLogger(CustomerConverter.class);
    public CustomerDTO customerDTOConverter(Customer customer){
        LOGGER.info("Inside customerDTOConverter()");
        CustomerDTO customerDTO=new CustomerDTO();
        customerDTO.setCId(customer.getCId());
        customerDTO.setCName(customer.getCName());
        customerDTO.setDob(customer.getDob());
        customerDTO.setAddress(customer.getAddress());
        return customerDTO;
    }

    public List<CustomerDTO> customerDTOListConverter(List<Customer> customers){
        LOGGER.info("Inside customerDTOListConverter()");
        return customers.stream().map(this::customerDTOConverter).toList();
    }

    public Customer customerConverter(CustomerDTO customerDTO){
        LOGGER.info("Inside customerConverter()");
        Customer customer=new Customer();
        customer.setCId(customerDTO.getCId());
        customer.setCName(customerDTO.getCName());
        customer.setDob(customerDTO.getDob());
        customer.setAddress(customerDTO.getAddress());
        return customer;
    }

    public List<Customer> customerListConverter(List<CustomerDTO> customerDTOs){
        LOGGER.info("Inside customerListConverter()");
        return customerDTOs.stream().map(this::customerConverter).toList();
    }
}
