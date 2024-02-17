package com.bank.app.domain.staging.entities;

import com.bank.app.util.AccountStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;
import java.util.List;

@Entity
@Table(name = "account")
@Getter
@Setter
@ToString
public class StgAccount {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "a_id")
	private Long accountId;

	@Enumerated(EnumType.STRING)
	private AccountStatus status;

	@Column(name = "opening_date")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@Temporal(TemporalType.TIMESTAMP)
	private Date openingDate;

	@Column(name = "closing_date", nullable = true)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@Temporal(TemporalType.TIMESTAMP)
	private Date closingDate;

	@OneToMany(mappedBy = "accountId")
	@JsonIgnore
	private List<StgRelationship> stgRelationships;

}
