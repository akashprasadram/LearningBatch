package com.bank.app.domain.runtime.entities;

import com.bank.app.util.AccountStatus;
import com.bank.app.util.ValidationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "a_id")
	private Long accountId;

	@Enumerated(EnumType.STRING)
	private AccountStatus status;

	@Column(name = "opening_date")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date openingDate;

	@Column(name = "closing_date")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date closingDate;

	@Min(value = 0)
	private Double balance;

	@OneToMany(mappedBy = "accountId")
	@JsonIgnore
	private List<Relationship> relationships;


}
