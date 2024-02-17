package com.bank.app.api.handler.staging;

import com.bank.app.api.staging.dto.CustomerDTO;
import com.bank.app.domain.staging.entities.StgCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerConverter {

    private static final Logger LOGGER= LoggerFactory.getLogger(CustomerConverter.class);
    public CustomerDTO customerDTOConverter(StgCustomer stgCustomer){
        LOGGER.info("Inside customerDTOConverter()");
        CustomerDTO customerDTO=new CustomerDTO();
        customerDTO.setCustId(stgCustomer.getCustId());
        customerDTO.setCustName(stgCustomer.getCustName());
        customerDTO.setDob(stgCustomer.getDob());
        customerDTO.setAddress(stgCustomer.getAddress());
        return customerDTO;
    }

    public List<CustomerDTO> customerDTOListConverter(List<StgCustomer> stgCustomers){
        LOGGER.info("Inside customerDTOListConverter()");
        return stgCustomers.stream().map(this::customerDTOConverter).toList();
    }

    public StgCustomer customerConverter(CustomerDTO customerDTO){
        LOGGER.info("Inside customerConverter()");
        StgCustomer stgCustomer =new StgCustomer();
        stgCustomer.setCustId(customerDTO.getCustId());
        stgCustomer.setCustName(customerDTO.getCustName());
        stgCustomer.setDob(customerDTO.getDob());
        stgCustomer.setAddress(customerDTO.getAddress());
        return stgCustomer;
    }

    public List<StgCustomer> customerListConverter(List<CustomerDTO> customerDTOs){
        LOGGER.info("Inside customerListConverter()");
        return customerDTOs.stream().map(this::customerConverter).toList();
    }
}
