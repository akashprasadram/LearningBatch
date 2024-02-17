package com.bank.app.domain.staging.entities;

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
public class StgRelationship {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
	private StgCustomer customerId;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
	private StgAccount accountId;
	
	@Enumerated(EnumType.STRING)
	private AccountType type;
}
