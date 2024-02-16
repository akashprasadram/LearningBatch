package com.bank.app.repo.prod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.app.entities.prod.Customer;


@Repository("prodCustomerRepo")
public interface CustomerRepo extends JpaRepository<Customer, Long> {

}
