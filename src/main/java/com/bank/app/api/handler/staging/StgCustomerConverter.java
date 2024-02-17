package com.bank.app.api.handler.staging;

import com.bank.app.api.staging.dto.StgCustomerDTO;
import com.bank.app.domain.staging.entities.StgCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StgCustomerConverter {

    private static final Logger LOGGER= LoggerFactory.getLogger(StgCustomerConverter.class);
    public StgCustomerDTO customerDTOConverter(StgCustomer stgCustomer){
        LOGGER.info("Inside customerDTOConverter()");
        StgCustomerDTO stgCustomerDTO =new StgCustomerDTO();
        stgCustomerDTO.setCustomerId(stgCustomer.getCustomerId());
        stgCustomerDTO.setCustomerName(stgCustomer.getCustomerName());
        stgCustomerDTO.setDob(stgCustomer.getDob());
        stgCustomerDTO.setAddress(stgCustomer.getAddress());
        return stgCustomerDTO;
    }

    public List<StgCustomerDTO> customerDTOListConverter(List<StgCustomer> stgCustomers){
        LOGGER.info("Inside customerDTOListConverter()");
        return stgCustomers.stream().map(this::customerDTOConverter).toList();
    }

    public StgCustomer customerConverter(StgCustomerDTO stgCustomerDTO){
        LOGGER.info("Inside customerConverter()");
        StgCustomer stgCustomer =new StgCustomer();
        stgCustomer.setCustomerId(stgCustomerDTO.getCustomerId());
        stgCustomer.setCustomerName(stgCustomerDTO.getCustomerName());
        stgCustomer.setDob(stgCustomerDTO.getDob());
        stgCustomer.setAddress(stgCustomerDTO.getAddress());
        return stgCustomer;
    }

    public List<StgCustomer> customerListConverter(List<StgCustomerDTO> stgCustomerDTOS){
        LOGGER.info("Inside customerListConverter()");
        return stgCustomerDTOS.stream().map(this::customerConverter).toList();
    }
}
