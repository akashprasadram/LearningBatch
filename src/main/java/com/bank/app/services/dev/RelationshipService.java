package com.bank.app.services.dev;

import java.util.List;

import com.bank.app.entities.dev.Relationship;
import com.bank.app.exceptions.DataCreationError;
import com.bank.app.exceptions.DataNotFoundException;

public interface RelationshipService {
	public List<Relationship> getRelationships();
	public Relationship getRelationshipById(Long id) throws DataNotFoundException;
	public Relationship createRelationship(Relationship relationship) throws DataCreationError;
	public List<Relationship> createRelationships(List<Relationship> relationships) throws DataCreationError;
	
}
