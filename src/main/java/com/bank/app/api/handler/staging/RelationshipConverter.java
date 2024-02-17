package com.bank.app.api.handler.staging;

import com.bank.app.api.staging.dto.AccountDTO;
import com.bank.app.api.staging.dto.CustomerDTO;
import com.bank.app.api.staging.dto.RelationshipDTO;
import com.bank.app.domain.staging.entities.StgAccount;
import com.bank.app.domain.staging.entities.StgCustomer;
import com.bank.app.domain.staging.entities.StgRelationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RelationshipConverter {

    private static final Logger LOGGER= LoggerFactory.getLogger(RelationshipConverter.class);

    private final CustomerConverter customerConverter;

    private final AccountConverter accountConverter;

    public RelationshipConverter(CustomerConverter customerConverter, AccountConverter accountConverter) {
        this.customerConverter = customerConverter;
        this.accountConverter = accountConverter;
    }

    public RelationshipDTO relationshipDTOConverter(StgRelationship stgRelationship){
        LOGGER.info("Inside relationshipDTOConverter()");
        RelationshipDTO relationshipDTO=new RelationshipDTO();
        relationshipDTO.setId(stgRelationship.getId());
        CustomerDTO customerDTO=customerConverter.customerDTOConverter(stgRelationship.getStgCustomerId());
        relationshipDTO.setCustomerId(customerDTO);
        AccountDTO accountDTO=accountConverter.accountDTOConverter(stgRelationship.getStgAccountId());
        relationshipDTO.setAccountId(accountDTO);
        relationshipDTO.setType(stgRelationship.getType());

        return relationshipDTO;
    }

    public List<RelationshipDTO> relationshipListDTOConverter(List<StgRelationship> stgRelationships){
        LOGGER.info("Inside relationshipListDTOConverter()");
        return stgRelationships.stream().map(this::relationshipDTOConverter).toList();
    }

    public StgRelationship relationshipConverter(RelationshipDTO relationshipDTO){
        LOGGER.info("Inside relationshipConverter()");
        StgRelationship stgRelationship =new StgRelationship();
        stgRelationship.setId(relationshipDTO.getId());
        StgCustomer stgCustomer =customerConverter.customerConverter(relationshipDTO.getCustomerId());
        stgRelationship.setStgCustomerId(stgCustomer);
        StgAccount stgAccount =accountConverter.accountConverter(relationshipDTO.getAccountId());
        stgRelationship.setStgAccountId(stgAccount);
        stgRelationship.setType(stgRelationship.getType());

        return stgRelationship;
    }

    public List<StgRelationship> relationshipListConverter(List<RelationshipDTO> relationships){
        LOGGER.info("Inside relationshipListConverter()");
        return relationships.stream().map(this::relationshipConverter).toList();
    }
}
