package com.bank.app.api.staging.controller;

import com.bank.app.api.handler.staging.StgCustomerConverter;
import com.bank.app.api.staging.dto.StgCustomerDTO;
import com.bank.app.api.staging.services.StgCustomerService;
import com.bank.app.domain.common.error.exceptions.DataIngestionError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;
import com.bank.app.domain.staging.entities.StgCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class stgCustomerController {

    private static final Logger LOGGER= LoggerFactory.getLogger(stgCustomerController.class);

    private final StgCustomerService stgCustomerService;

    private final StgCustomerConverter stgCustomerConverter;


    public stgCustomerController(StgCustomerService stgCustomerService, StgCustomerConverter stgCustomerConverter) {
        this.stgCustomerService = stgCustomerService;
        this.stgCustomerConverter = stgCustomerConverter;
    }

    @PostMapping("/customer/")
    public ResponseEntity<StgCustomerDTO> createCustomer(@RequestBody StgCustomerDTO stgCustomerDTO) throws DataIngestionError {
        LOGGER.info("Inside createCustomer() with Customer : {}", stgCustomerDTO);
        StgCustomer stgCustomer = stgCustomerConverter.customerConverter(stgCustomerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(stgCustomerService.createCustomer(stgCustomer));
    }

    @PostMapping("/customers/")
    public ResponseEntity<List<StgCustomerDTO>> createAllCustomer(@RequestBody List<StgCustomerDTO> stgCustomerDTOS) throws DataIngestionError {
        LOGGER.info("Inside createAllCustomer() with Customers : {}", stgCustomerDTOS);
        List<StgCustomer> stgCustomers = stgCustomerConverter.customerListConverter(stgCustomerDTOS);
        return ResponseEntity.status(HttpStatus.CREATED).body(stgCustomerService.createCustomers(stgCustomers));
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<StgCustomerDTO> getCustomerById(@PathVariable("id") Long id) throws DataNotFoundException {
        LOGGER.info("Inside getCustomerById() with Id : {}",id);
        return ResponseEntity.ok(stgCustomerService.getCustomerById(id));
    }

    @GetMapping("/customer/")
    public ResponseEntity<List<StgCustomerDTO>> getCustomers(){
        LOGGER.info("Inside getCustomers()");
        return ResponseEntity.ok(stgCustomerService.getCustomers());
    }
}
