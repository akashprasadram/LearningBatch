package com.bank.app.entities.prod;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "account")
@Getter
@Setter
@ToString
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "a_id")
	private Long aId;

	private boolean status;

	@Column(name = "opening_date")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date openingDate;

	@Column(name = "closing_date")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date closingDate;

	@Min(value = 0)
	private Double amount;

	@OneToMany(mappedBy = "accountId")
	@JsonIgnore
	private List<Relationship> relationships;

}
