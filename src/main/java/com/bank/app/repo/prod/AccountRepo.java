package com.bank.app.repo.prod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.app.entities.prod.Account;

@Repository("prodAccountRepo")
public interface AccountRepo extends JpaRepository<Account, Long> {

}
