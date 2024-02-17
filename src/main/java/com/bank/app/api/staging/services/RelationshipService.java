package com.bank.app.api.staging.services;

import java.util.List;

import com.bank.app.api.staging.dto.RelationshipDTO;
import com.bank.app.domain.staging.entities.Relationship;
import com.bank.app.domain.common.error.exceptions.DataCreationError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;

public interface RelationshipService {
	public List<RelationshipDTO> getRelationships();
	public RelationshipDTO getRelationshipById(Long id) throws DataNotFoundException;
	public RelationshipDTO createRelationship(Relationship relationship) throws DataCreationError;
	public List<RelationshipDTO> createRelationships(List<Relationship> relationships) throws DataCreationError;
	
}
