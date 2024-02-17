package com.bank.app.api.staging.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	private Long custId;

	private String custName;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@Temporal(TemporalType.DATE)
	private Date dob;

	@Size(min = 2, max = 50)
	private String address;

	@JsonIgnore
    private List<RelationshipDTO> relationshipDTOS;

}
