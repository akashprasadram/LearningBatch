package com.bank.app.domain.staging.repository;

import com.bank.app.domain.staging.entities.StgAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StgAccountRepo extends JpaRepository<StgAccount, Long> {

}
