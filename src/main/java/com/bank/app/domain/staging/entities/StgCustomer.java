package com.bank.app.domain.staging.entities;

import java.util.Date;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "customer")
@Setter
@Getter
@ToString
public class StgCustomer {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "c_id")
	private Long customerId;

	@Column(name = "c_name")
	@Size(min = 2, max = 20)
	private String customerName;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@Temporal(TemporalType.DATE)
	private Date dob;

	@Size(min = 2, max = 50)
	private String address;

	@OneToMany(mappedBy = "customerId")
	@JsonIgnore
	private List<StgRelationship> stgRelationships;


}
