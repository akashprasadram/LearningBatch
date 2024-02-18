package com.bank.app.domain.staging.repository;

import com.bank.app.domain.staging.entities.StgAccount;
import com.bank.app.domain.staging.entities.StgCustomer;
import com.bank.app.domain.staging.entities.StgRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StgRelationshipRepo extends JpaRepository<StgRelationship, Long> {

    List<StgRelationship> findByAccountId(StgAccount stgAccount);

    List<StgRelationship> findByCustomerId(StgCustomer stgCustomer);
}
