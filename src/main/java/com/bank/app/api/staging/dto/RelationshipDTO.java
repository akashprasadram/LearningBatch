package com.bank.app.api.staging.dto;

import com.bank.app.util.AccountType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Setter
@Getter
@ToString
public class RelationshipDTO {

	private Long id;

	private CustomerDTO customerId;

	private AccountDTO accountId;

	@Enumerated(EnumType.STRING)
	private AccountType type;
}
