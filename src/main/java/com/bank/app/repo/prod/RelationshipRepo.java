package com.bank.app.repo.prod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.app.entities.prod.Relationship;

@Repository("prodRelationshipRepo")
public interface RelationshipRepo extends JpaRepository<Relationship, Long> {

}
