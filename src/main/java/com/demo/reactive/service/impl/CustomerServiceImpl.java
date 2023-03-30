package com.demo.reactive.service.impl;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.demo.reactive.dto.CustomerRequest;
import com.demo.reactive.dto.CustomerResponse;
import com.demo.reactive.model.Customer;
import com.demo.reactive.repository.CustomerRepository;
import com.demo.reactive.service.CustomerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public Mono<CustomerResponse> createCustomer(CustomerRequest request) {
		return Mono.fromCallable(() -> constructorCustomer(request))
				.flatMap(r -> Mono.fromFuture(CompletableFuture.supplyAsync(() -> customerRepository.save(r)))
				.map(this::constructorCustomerResponse));
	}

	@Override
	public Mono<CustomerResponse> updateCustomer(Long customerId, CustomerRequest request) {
		return find(customerId)
				.map(r -> {
						r.setFirstName(request.getFirstName());
						r.setLastName(request.getLastName());
						r.setAddress(request.getAddress());
						r.setPhoneNumber(request.getPhoneNumber());
			
						return r;
					})
				.flatMap(customer -> Mono.fromFuture(CompletableFuture.supplyAsync(() -> customerRepository.save(customer)))
				.map(this::constructorCustomerResponse));
	}

	@Override
	public Flux<CustomerResponse> getCustomers() {
		return Mono.fromFuture(CompletableFuture.supplyAsync(() -> customerRepository.findAll()))
				.flatMapMany(Flux::fromIterable)
				.map(this::constructorCustomerResponse)
				.switchIfEmpty(Flux.empty());
	}	

	@Override
	public Mono<CustomerResponse> getCustomer(Long customerId) {
		return find(customerId)
				.map(this::constructorCustomerResponse);
	}	

	@Override
	public Mono<Void> deleteCustomerById(Long customerId) {		
		customerRepository.deleteById(customerId);
		return Mono.empty();
		/*
		return  Mono.fromFuture(CompletableFuture.supplyAsync(() -> customerRepository.findById(customerId)))
				.flatMap(r -> {					
					 if (r.isPresent()) {
						 customerRepository.delete(r.get());
						 return Mono.empty();
					 }
					
					return Mono.empty();
				});
		*/
	}

	private Mono<Customer> find(Long customerId) {
		return Mono.fromFuture(CompletableFuture.supplyAsync(() -> customerRepository.findById(customerId)))
				.flatMap(r -> r.isPresent() ? Mono.fromCallable(r::get) : Mono.empty());
	}

	private Customer constructorCustomer(CustomerRequest request) {

		Customer response = new Customer();
		response.setFirstName(request.getFirstName());
		response.setLastName(request.getLastName());
		response.setAddress(request.getAddress());
		response.setPhoneNumber(request.getPhoneNumber());

		return response;
	}

	private CustomerResponse constructorCustomerResponse(Customer customer) {
		return CustomerResponse.builder()
				.customerId(customer.getCustomerId())
				.firstName(customer.getFirstName())
				.lastName(customer.getLastName())
				.address(customer.getAddress())
				.phoneNumber(customer.getPhoneNumber()).build();
	}

}