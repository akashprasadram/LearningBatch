package com.bank.app.api.staging.services;

import com.bank.app.api.staging.dto.StgRelationshipDTO;
import com.bank.app.domain.common.error.exceptions.DataIngestionError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;
import com.bank.app.domain.staging.entities.StgRelationship;

import java.util.List;

public interface StgRelationshipService {
	public List<StgRelationshipDTO> getRelationships();
	public StgRelationshipDTO getRelationshipById(Long id) throws DataNotFoundException;
	public StgRelationshipDTO createRelationship(StgRelationship stgRelationship) throws DataIngestionError;
	public List<StgRelationshipDTO> createRelationships(List<StgRelationship> stgRelationships) throws DataIngestionError;
	
}
