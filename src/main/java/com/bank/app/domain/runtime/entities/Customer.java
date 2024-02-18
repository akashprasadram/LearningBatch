package com.bank.app.domain.runtime.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@ToString
public class Customer {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "c_id")
	private Long customerId;

	@Column(name = "c_name")
	@Size(min = 2, max = 20)
	private String customerName;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dob;

	@Size(min = 2, max = 50)
	private String address;
	
	@OneToMany(mappedBy = "customerId")
	@JsonIgnore
    private List<Relationship> relationships;

}
