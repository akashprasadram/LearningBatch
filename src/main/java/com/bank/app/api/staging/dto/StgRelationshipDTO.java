package com.bank.app.api.staging.dto;

import com.bank.app.util.AccountType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Setter
@Getter
@ToString
public class StgRelationshipDTO {

	private Long id;

	private StgCustomerDTO customerId;

	private StgAccountDTO accountId;

	@Enumerated(EnumType.STRING)
	private AccountType type;
}
