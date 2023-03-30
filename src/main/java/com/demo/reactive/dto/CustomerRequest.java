package com.demo.reactive.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CustomerRequest {

  @NotEmpty(message = "Please provide a first name")
	private String firstName;

  @NotEmpty(message = "Please provide a last name")
	private String lastName;
	
  @NotEmpty(message = "Please provide an address")
	private String address;
	
  @NotEmpty(message = "Please provide a phone number")
	private String phoneNumber;
	
}