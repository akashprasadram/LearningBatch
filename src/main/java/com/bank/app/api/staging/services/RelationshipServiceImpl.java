package com.bank.app.api.staging.services;

import java.util.List;
import java.util.Optional;

import com.bank.app.api.handler.staging.RelationshipConverter;
import com.bank.app.api.staging.dto.RelationshipDTO;
import com.bank.app.domain.staging.entities.StgRelationship;
import org.springframework.stereotype.Service;

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
		List<StgRelationship> stgRelationships = relationshipRepo.findAll();
		return relationshipConverter.relationshipListDTOConverter(stgRelationships);
	}

	@Override
	public RelationshipDTO getRelationshipById(Long id) throws DataNotFoundException {
		Optional<StgRelationship> relationship = relationshipRepo.findById(id);
		if(relationship.isEmpty()) {
			throw new DataNotFoundException("Relationship not found with id : "+id);
		}
		StgRelationship resultStgRelationship = relationship.get();
		return relationshipConverter.relationshipDTOConverter(resultStgRelationship);
	}

	@Override
	public RelationshipDTO createRelationship(StgRelationship stgRelationship) throws DataCreationError {
		StgRelationship savedStgRelationship = relationshipRepo.save(stgRelationship);
		if(savedStgRelationship.getId()==0) {
			throw new DataCreationError("Unable to create relationship");
		}
		return relationshipConverter.relationshipDTOConverter(savedStgRelationship);
	}

	@Override
	public List<RelationshipDTO> createRelationships(List<StgRelationship> stgRelationships) throws DataCreationError {
		List<StgRelationship> savedStgRelationships =relationshipRepo.saveAll(stgRelationships);
		if(savedStgRelationships.isEmpty()) {
			throw new DataCreationError("Unable to create relationships");
		}
		return relationshipConverter.relationshipListDTOConverter(savedStgRelationships);
	}

}
