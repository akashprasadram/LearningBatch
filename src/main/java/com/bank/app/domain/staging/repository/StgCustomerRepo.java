package com.bank.app.domain.staging.repository;

import com.bank.app.domain.staging.entities.StgCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StgCustomerRepo extends JpaRepository<StgCustomer, Long> {

}
