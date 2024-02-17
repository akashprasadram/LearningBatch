package com.bank.app.api.staging.controller;

import com.bank.app.api.handler.staging.CustomerConverter;
import com.bank.app.api.staging.dto.CustomerDTO;
import com.bank.app.api.staging.services.CustomerService;
import com.bank.app.domain.common.error.exceptions.DataCreationError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;
import com.bank.app.domain.staging.entities.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    private static final Logger LOGGER= LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    private final CustomerConverter customerConverter;


    public CustomerController(CustomerService customerService, CustomerConverter customerConverter) {
        this.customerService = customerService;
        this.customerConverter = customerConverter;
    }

    @PostMapping("/customer/")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) throws DataCreationError {
        LOGGER.info("Inside createCustomer() with Customer : {}",customerDTO);
        // Add debugging statement to log cName
        LOGGER.info("cName in CustomerDTO: {}", customerDTO.getCName());
        Customer customer=customerConverter.customerConverter(customerDTO);

        // Add debugging statement to log cName in Customer
        LOGGER.info("cName in Customer: {}", customer.getCName());
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customer));
    }

    @PostMapping("/customers/")
    public ResponseEntity<List<CustomerDTO>> createAllCustomer(@RequestBody List<CustomerDTO> customerDTOs) throws DataCreationError {
        LOGGER.info("Inside createAllCustomer() with Customers : {}",customerDTOs);
        List<Customer> customers=customerConverter.customerListConverter(customerDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomers(customers));
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") Long id) throws DataNotFoundException {
        LOGGER.info("Inside getCustomerById() with Id : {}",id);
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping("/customer/")
    public ResponseEntity<List<CustomerDTO>> getCustomers(){
        LOGGER.info("Inside getCustomers()");
        return ResponseEntity.ok(customerService.getCustomers());
    }
}
