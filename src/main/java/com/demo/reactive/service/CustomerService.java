package com.demo.reactive.service;

import com.demo.reactive.dto.CustomerRequest;
import com.demo.reactive.dto.CustomerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

	Mono<CustomerResponse> createCustomer(CustomerRequest customer);

	Mono<CustomerResponse> updateCustomer(Long customerId, CustomerRequest request);
	
	Flux<CustomerResponse> getCustomers();

	Mono<CustomerResponse> getCustomer(Long customerId);
	
	Mono<Void> deleteCustomerById(Long customerId);

}