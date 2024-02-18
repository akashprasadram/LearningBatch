package com.bank.app.domain.runtime.repository;

import com.bank.app.domain.runtime.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

}
