package com.bank.app.api.handler.staging;

import com.bank.app.api.staging.dto.AccountDTO;
import com.bank.app.api.staging.dto.CustomerDTO;
import com.bank.app.api.staging.dto.RelationshipDTO;
import com.bank.app.domain.staging.entities.Account;
import com.bank.app.domain.staging.entities.Customer;
import com.bank.app.domain.staging.entities.Relationship;
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

    public RelationshipDTO relationshipDTOConverter(Relationship relationship){
        LOGGER.info("Inside relationshipDTOConverter()");
        RelationshipDTO relationshipDTO=new RelationshipDTO();
        relationshipDTO.setId(relationship.getId());
        CustomerDTO customerDTO=customerConverter.customerDTOConverter(relationship.getCustomerId());
        relationshipDTO.setCustomerId(customerDTO);
        AccountDTO accountDTO=accountConverter.accountDTOConverter(relationship.getAccountId());
        relationshipDTO.setAccountId(accountDTO);
        relationshipDTO.setType(relationship.getType());

        return relationshipDTO;
    }

    public List<RelationshipDTO> relationshipListDTOConverter(List<Relationship> relationships){
        LOGGER.info("Inside relationshipListDTOConverter()");
        return relationships.stream().map(this::relationshipDTOConverter).toList();
    }

    public Relationship relationshipConverter(RelationshipDTO relationshipDTO){
        LOGGER.info("Inside relationshipConverter()");
        Relationship relationship=new Relationship();
        relationship.setId(relationshipDTO.getId());
        Customer customer=customerConverter.customerConverter(relationshipDTO.getCustomerId());
        relationship.setCustomerId(customer);
        Account account=accountConverter.accountConverter(relationshipDTO.getAccountId());
        relationship.setAccountId(account);
        relationship.setType(relationship.getType());

        return relationship;
    }

    public List<Relationship> relationshipListConverter(List<RelationshipDTO> relationships){
        LOGGER.info("Inside relationshipListConverter()");
        return relationships.stream().map(this::relationshipConverter).toList();
    }
}
