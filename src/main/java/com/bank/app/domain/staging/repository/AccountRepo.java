package com.bank.app.domain.staging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.app.domain.staging.entities.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

}
