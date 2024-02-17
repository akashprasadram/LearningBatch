package com.bank.app.api.staging.services;

import java.util.List;
import java.util.Optional;

import com.bank.app.api.handler.staging.RelationshipConverter;
import com.bank.app.api.staging.dto.RelationshipDTO;
import org.springframework.stereotype.Service;

import com.bank.app.domain.staging.entities.Relationship;
import com.bank.app.domain.common.error.exceptions.DataCreationError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;
import com.bank.app.domain.staging.repository.RelationshipRepo;

@Service
public class RelationshipServiceImpl implements RelationshipService {


	private final RelationshipConverter relationshipConverter;
	private final RelationshipRepo relationshipRepo;


	public RelationshipServiceImpl(RelationshipConverter relationshipConverter, RelationshipRepo relationshipRepo) {
		this.relationshipConverter = relationshipConverter;
		this.relationshipRepo = relationshipRepo;
	}

	@Override
	public List<RelationshipDTO> getRelationships() {
		List<Relationship> relationships = relationshipRepo.findAll();
		return relationshipConverter.relationshipListDTOConverter(relationships);
	}

	@Override
	public RelationshipDTO getRelationshipById(Long id) throws DataNotFoundException {
		Optional<Relationship> relationship = relationshipRepo.findById(id);
		if(relationship.isEmpty()) {
			throw new DataNotFoundException("Relationship not found with id : "+id);
		}
		Relationship resultRelationship = relationship.get();
		return relationshipConverter.relationshipDTOConverter(resultRelationship);
	}

	@Override
	public RelationshipDTO createRelationship(Relationship relationship) throws DataCreationError {
		Relationship savedRelationship = relationshipRepo.save(relationship);
		if(savedRelationship.getId()==0) {
			throw new DataCreationError("Unable to create relationship");
		}
		return relationshipConverter.relationshipDTOConverter(savedRelationship);
	}

	@Override
	public List<RelationshipDTO> createRelationships(List<Relationship> relationships) throws DataCreationError {
		List<Relationship> savedRelationships=relationshipRepo.saveAll(relationships);
		if(savedRelationships.isEmpty()) {
			throw new DataCreationError("Unable to create relationships");
		}
		return relationshipConverter.relationshipListDTOConverter(savedRelationships);
	}

}
