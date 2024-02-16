package com.bank.app.repo.dev;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.app.entities.dev.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

}
