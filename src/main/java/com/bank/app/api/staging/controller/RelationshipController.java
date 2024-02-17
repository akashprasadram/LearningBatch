package com.bank.app.api.staging.controller;

import com.bank.app.api.handler.staging.RelationshipConverter;
import com.bank.app.api.staging.dto.RelationshipDTO;
import com.bank.app.api.staging.services.RelationshipService;
import com.bank.app.domain.common.error.exceptions.DataCreationError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;
import com.bank.app.domain.staging.entities.StgRelationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RelationshipController {

    private static final Logger LOGGER= LoggerFactory.getLogger(RelationshipController.class);
    private final RelationshipService relationshipService;

    private final RelationshipConverter relationshipConverter;


    public RelationshipController(RelationshipService relationshipService, RelationshipConverter relationshipConverter) {
        this.relationshipService = relationshipService;
        this.relationshipConverter = relationshipConverter;
    }

    @PostMapping("/relationship/")
    public ResponseEntity<RelationshipDTO> createRelationship(@RequestBody RelationshipDTO relationshipDTO) throws DataCreationError {
        LOGGER.info("Inside createRelationship() with Relationship : {}",relationshipDTO);
        StgRelationship stgRelationship =relationshipConverter.relationshipConverter(relationshipDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(relationshipService.createRelationship(stgRelationship));
    }

    @PostMapping("/relationships/")
    public ResponseEntity<List<RelationshipDTO>> createAllRelationship(@RequestBody List<RelationshipDTO> relationshipDTOs) throws DataCreationError {
        LOGGER.info("Inside createAllRelationship() with Relationships : {}",relationshipDTOs);
        List<StgRelationship> stgRelationships =relationshipConverter.relationshipListConverter(relationshipDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(relationshipService.createRelationships(stgRelationships));
    }

    @GetMapping("/relationship/{id}")
    public ResponseEntity<RelationshipDTO> getRelationshipById(@PathVariable("id") Long id) throws DataNotFoundException {
        LOGGER.info("Inside getRelationshipById() with Id : {}",id);
        return ResponseEntity.ok(relationshipService.getRelationshipById(id));
    }

    @GetMapping("/relationship/")
    public ResponseEntity<List<RelationshipDTO>> getRelationships() {
        LOGGER.info("Inside getRelationships()");
        return ResponseEntity.ok(relationshipService.getRelationships());
    }
}
