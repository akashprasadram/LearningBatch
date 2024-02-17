package com.bank.app.domain.staging.repository;

import com.bank.app.domain.staging.entities.StgRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipRepo extends JpaRepository<StgRelationship, Long> {

}
