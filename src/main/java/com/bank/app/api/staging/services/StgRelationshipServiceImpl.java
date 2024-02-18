package com.bank.app.api.staging.services;

import java.util.List;
import java.util.Optional;

import com.bank.app.api.handler.staging.StgRelationshipConverter;
import com.bank.app.api.staging.dto.StgRelationshipDTO;
import com.bank.app.domain.staging.entities.StgRelationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bank.app.domain.common.error.exceptions.DataIngestionError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;
import com.bank.app.domain.staging.repository.StgRelationshipRepo;

@Service
public class StgRelationshipServiceImpl implements StgRelationshipService {

	private static final Logger LOGGER= LoggerFactory.getLogger(StgRelationshipServiceImpl.class);

	private final StgRelationshipConverter stgRelationshipConverter;
	private final StgRelationshipRepo stgRelationshipRepo;


	public StgRelationshipServiceImpl(StgRelationshipConverter stgRelationshipConverter, StgRelationshipRepo stgRelationshipRepo) {
		this.stgRelationshipConverter = stgRelationshipConverter;
		this.stgRelationshipRepo = stgRelationshipRepo;
	}

	@Override
	public List<StgRelationshipDTO> getRelationships() {
		LOGGER.info("Inside getRelationships()");
		List<StgRelationship> stgRelationships = stgRelationshipRepo.findAll();
		return stgRelationshipConverter.relationshipListDTOConverter(stgRelationships);
	}

	@Override
	public StgRelationshipDTO getRelationshipById(Long id) throws DataNotFoundException {
		LOGGER.info("Inside getRelationshipById() with Id : {}",id);
		Optional<StgRelationship> relationship = stgRelationshipRepo.findById(id);
		if(relationship.isEmpty()) {
			throw new DataNotFoundException("Relationship not found with id : "+id);
		}
		StgRelationship resultStgRelationship = relationship.get();
		return stgRelationshipConverter.relationshipDTOConverter(resultStgRelationship);
	}

	@Override
	public StgRelationshipDTO createRelationship(StgRelationship stgRelationship) throws DataIngestionError {
		LOGGER.info("Inside createRelationship() with StgRelationship : {}",stgRelationship);
		StgRelationship savedStgRelationship = stgRelationshipRepo.save(stgRelationship);
		if(savedStgRelationship.getId()==0) {
			throw new DataIngestionError("Unable to create relationship");
		}
		return stgRelationshipConverter.relationshipDTOConverter(savedStgRelationship);
	}

	@Override
	public List<StgRelationshipDTO> createRelationships(List<StgRelationship> stgRelationships) throws DataIngestionError {
		LOGGER.info("Inside createRelationship() with stgRelationships : {}",stgRelationships);
		List<StgRelationship> savedStgRelationships = stgRelationshipRepo.saveAll(stgRelationships);
		if(savedStgRelationships.isEmpty()) {
			throw new DataIngestionError("Unable to create relationships");
		}
		return stgRelationshipConverter.relationshipListDTOConverter(savedStgRelationships);
	}

}
