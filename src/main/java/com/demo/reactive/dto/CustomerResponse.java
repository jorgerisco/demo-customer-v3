package com.demo.reactive.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@Builder
public class CustomerResponse {

	private Long customerId;
	
	private String firstName;

	private String lastName;
	
	private String address;
	
	private String phoneNumber;
	
}