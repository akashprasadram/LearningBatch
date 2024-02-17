package com.bank.app.domain.staging.entities;

import javax.persistence.*;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
	private Customer customerId;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
	private Account accountId;
	
	@Enumerated(EnumType.STRING)
	private AccountType type;
}
