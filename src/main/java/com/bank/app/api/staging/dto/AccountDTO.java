package com.bank.app.api.staging.dto;

import com.bank.app.domain.staging.entities.AccountStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

	private Long aId;

	@Enumerated(EnumType.STRING)
	private AccountStatus status;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@Temporal(TemporalType.TIMESTAMP)
	private Date openingDate;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@Temporal(TemporalType.TIMESTAMP)
	private Date closingDate;

	@JsonIgnore
	private List<RelationshipDTO> relationshipDTOS;

}
