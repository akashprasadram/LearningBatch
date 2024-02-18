package com.bank.app.domain.runtime.repository;

import com.bank.app.domain.runtime.entities.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipRepo extends JpaRepository<Relationship, Long> {

}
