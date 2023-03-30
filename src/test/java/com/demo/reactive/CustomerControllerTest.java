package com.demo.reactive;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.demo.reactive.dto.CustomerResponse;
import com.demo.reactive.repository.CustomerRepository;
import com.demo.reactive.service.CustomerService;

import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@WebFluxTest(CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private CustomerRepository repository;

	@MockBean
	private CustomerService service;
	
	@Test
	public void listCustomers() {		
		CustomerResponse customer1 = CustomerResponse.builder()
				.customerId(1L)
				.firstName("Jorge")
				.lastName("Risco")
				.address("Av. Arequipa 123")
				.phoneNumber("900600300")
				.build();

		CustomerResponse customer2 = CustomerResponse.builder()
				.customerId(2L)
				.firstName("Raul")
				.lastName("Perez")
				.address("Av. Tacna 123")
				.phoneNumber("999666333")
				.build();
		
		BDDMockito.given(this.service.getCustomers()).willReturn(Flux.just(customer1, customer2));
		
		this.webTestClient.get().uri("/customers").accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectBodyList(CustomerResponse.class)
				.hasSize(2)
				.contains(customer1, customer2);
	}	

}