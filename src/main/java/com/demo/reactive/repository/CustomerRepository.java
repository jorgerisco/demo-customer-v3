package com.demo.reactive.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.reactive.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	Customer findById(long customerId);

}