package com.bank.app.domain.staging.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "c_id")
	private Long cId;

	@Column(name = "c_name")
	@Size(min = 2, max = 20)
	private String cName;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@Temporal(TemporalType.DATE)
	private Date dob;

	@OneToMany(mappedBy = "customerId")
	@JsonIgnore
	private List<Relationship> relationships;

	@Size(min = 2, max = 50)
	private String address;
}
