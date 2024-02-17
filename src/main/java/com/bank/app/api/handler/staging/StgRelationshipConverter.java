package com.bank.app.api.handler.staging;

import com.bank.app.api.staging.dto.StgAccountDTO;
import com.bank.app.api.staging.dto.StgCustomerDTO;
import com.bank.app.api.staging.dto.StgRelationshipDTO;
import com.bank.app.domain.staging.entities.StgAccount;
import com.bank.app.domain.staging.entities.StgCustomer;
import com.bank.app.domain.staging.entities.StgRelationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StgRelationshipConverter {

    private static final Logger LOGGER= LoggerFactory.getLogger(StgRelationshipConverter.class);

    private final StgCustomerConverter stgCustomerConverter;

    private final StgAccountConverter stgAccountConverter;

    public StgRelationshipConverter(StgCustomerConverter stgCustomerConverter, StgAccountConverter stgAccountConverter) {
        this.stgCustomerConverter = stgCustomerConverter;
        this.stgAccountConverter = stgAccountConverter;
    }

    public StgRelationshipDTO relationshipDTOConverter(StgRelationship stgRelationship){
        LOGGER.info("Inside relationshipDTOConverter()");
        StgRelationshipDTO stgRelationshipDTO =new StgRelationshipDTO();
        stgRelationshipDTO.setId(stgRelationship.getId());
        StgCustomerDTO stgCustomerDTO = stgCustomerConverter.customerDTOConverter(stgRelationship.getCustomerId());
        stgRelationshipDTO.setCustomerId(stgCustomerDTO);
        StgAccountDTO stgAccountDTO = stgAccountConverter.accountDTOConverter(stgRelationship.getAccountId());
        stgRelationshipDTO.setAccountId(stgAccountDTO);
        stgRelationshipDTO.setType(stgRelationship.getType());

        return stgRelationshipDTO;
    }

    public List<StgRelationshipDTO> relationshipListDTOConverter(List<StgRelationship> stgRelationships){
        LOGGER.info("Inside relationshipListDTOConverter()");
        return stgRelationships.stream().map(this::relationshipDTOConverter).toList();
    }

    public StgRelationship relationshipConverter(StgRelationshipDTO stgRelationshipDTO){
        LOGGER.info("Inside relationshipConverter()");
        StgRelationship stgRelationship =new StgRelationship();
        stgRelationship.setId(stgRelationshipDTO.getId());
        StgCustomer stgCustomer = stgCustomerConverter.customerConverter(stgRelationshipDTO.getCustomerId());
        stgRelationship.setCustomerId(stgCustomer);
        StgAccount stgAccount = stgAccountConverter.accountConverter(stgRelationshipDTO.getAccountId());
        stgRelationship.setAccountId(stgAccount);
        stgRelationship.setType(stgRelationshipDTO.getType());
        LOGGER.info("Inside relationshipConverter() before convert : {} after convert : {}",stgRelationshipDTO,stgRelationship);
        return stgRelationship;
    }

    public List<StgRelationship> relationshipListConverter(List<StgRelationshipDTO> relationships){
        LOGGER.info("Inside relationshipListConverter()");
        return relationships.stream().map(this::relationshipConverter).toList();
    }
}
