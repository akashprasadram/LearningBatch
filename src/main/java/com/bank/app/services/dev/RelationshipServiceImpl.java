package com.bank.app.services.dev;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bank.app.entities.dev.Relationship;
import com.bank.app.exceptions.DataCreationError;
import com.bank.app.exceptions.DataNotFoundException;
import com.bank.app.repo.dev.RelationshipRepo;

@Service
public class RelationshipServiceImpl implements RelationshipService {

	
	private RelationshipRepo relationshipRepo; 
	
	
	public RelationshipServiceImpl(RelationshipRepo relationshipRepo) {
		super();
		this.relationshipRepo = relationshipRepo;
	}

	@Override
	public List<Relationship> getRelationships() {
		return relationshipRepo.findAll();
	}

	@Override
	public Relationship getRelationshipById(Long id) throws DataNotFoundException {
		Optional<Relationship> relationship = relationshipRepo.findById(id);
		if(relationship.isEmpty()) {
			throw new DataNotFoundException("Relationship not found with id : "+id);
		}
		return relationship.get();
	}

	@Override
	public Relationship createRelationship(Relationship relationship) throws DataCreationError {
		Relationship savedRelationship = relationshipRepo.save(relationship);
		if(savedRelationship.getId()==0) {
			throw new DataCreationError("Unable to create relationship");
		}
		return savedRelationship;
	}

	@Override
	public List<Relationship> createRelationships(List<Relationship> relationships) throws DataCreationError {
		List<Relationship> savedRelationships=relationshipRepo.saveAll(relationships);
		if(savedRelationships.isEmpty()) {
			throw new DataCreationError("Unable to create relationships");
		}
		return savedRelationships;
	}

}
