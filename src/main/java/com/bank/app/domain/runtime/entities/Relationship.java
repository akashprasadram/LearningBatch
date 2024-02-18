package com.bank.app.domain.runtime.entities;

import com.bank.app.util.AccountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "relationship")
@Setter
@Getter
@ToString
public class Relationship {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "customer_id")
	private Customer customerId;
	
	@ManyToOne
    @JoinColumn(name = "account_id")
	private Account accountId;
	
	@Enumerated(EnumType.STRING)
	private AccountType type;
}
