package com.bank.app.repo.dev;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.app.entities.dev.Relationship;

@Repository
public interface RelationshipRepo extends JpaRepository<Relationship, Long> {

}
