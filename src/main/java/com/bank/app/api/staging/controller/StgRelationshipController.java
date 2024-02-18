package com.bank.app.api.staging.controller;

import com.bank.app.api.handler.staging.StgRelationshipConverter;
import com.bank.app.api.staging.dto.StgRelationshipDTO;
import com.bank.app.api.staging.services.StgRelationshipService;
import com.bank.app.domain.common.error.exceptions.DataIngestionError;
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
public class StgRelationshipController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StgRelationshipController.class);
    private final StgRelationshipService stgRelationshipService;

    private final StgRelationshipConverter stgRelationshipConverter;


    public StgRelationshipController(StgRelationshipService stgRelationshipService, StgRelationshipConverter stgRelationshipConverter) {
        this.stgRelationshipService = stgRelationshipService;
        this.stgRelationshipConverter = stgRelationshipConverter;
    }

    @PostMapping("/relationship/")
    public ResponseEntity<StgRelationshipDTO> createRelationship(@RequestBody StgRelationshipDTO stgRelationshipDTO) throws DataIngestionError {
        LOGGER.info("Inside createRelationship() with Relationship : {}", stgRelationshipDTO);
        StgRelationship stgRelationship = stgRelationshipConverter.relationshipConverter(stgRelationshipDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(stgRelationshipService.createRelationship(stgRelationship));
    }

    @PostMapping("/relationships/")
    public ResponseEntity<List<StgRelationshipDTO>> createAllRelationship(@RequestBody List<StgRelationshipDTO> stgRelationshipDTOS) throws DataIngestionError {
        LOGGER.info("Inside createAllRelationship() with Relationships : {}", stgRelationshipDTOS);
        List<StgRelationship> stgRelationships = stgRelationshipConverter.relationshipListConverter(stgRelationshipDTOS);
        return ResponseEntity.status(HttpStatus.CREATED).body(stgRelationshipService.createRelationships(stgRelationships));
    }

    @GetMapping("/relationship/{id}")
    public ResponseEntity<StgRelationshipDTO> getRelationshipById(@PathVariable("id") Long id) throws DataNotFoundException {
        LOGGER.info("Inside getRelationshipById() with Id : {}", id);
        return ResponseEntity.ok(stgRelationshipService.getRelationshipById(id));
    }

    @GetMapping("/relationship/")
    public ResponseEntity<List<StgRelationshipDTO>> getRelationships() {
        LOGGER.info("Inside getRelationships()");
        return ResponseEntity.ok(stgRelationshipService.getRelationships());
    }
}
