package com.demo.reactive;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.reactive.dto.CustomerRequest;
import com.demo.reactive.dto.CustomerResponse;
import com.demo.reactive.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Tag(description = "Api de gestión de clientes", name = "Customer-Controller")
public class CustomerController {

	private CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping(value = "/customers")
  @ResponseStatus(HttpStatus.CREATED)
	@Operation(description = "Creación de Cliente", summary = "Creación de Cliente")
	public Mono<ResponseEntity<CustomerResponse>> createCustomer(@Valid @RequestBody CustomerRequest customer) {
		return customerService.createCustomer(customer)
				.map(ResponseEntity::ok);
	}

	@GetMapping(value = "/customers")
	@Operation(description = "Obtener listado de clientes", summary = "Obtener listado de clientes")
	public Flux<CustomerResponse> getCustomers() {
		return customerService.getCustomers();
	}
	
	@GetMapping(value = "/customers/{customerId}")
	@Operation(description = "Obtener cliente", summary = "Obtener cliente")
	public Mono<ResponseEntity<CustomerResponse>> getCustomer(@PathVariable Long customerId) {
		return customerService.getCustomer(customerId)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
	}	

	@PutMapping(value = "/customers/{customerId}")
	@Operation(description = "Actualizar cliente", summary = "Actualizar cliente")
	public Mono<ResponseEntity<CustomerResponse>> updateCustomers(@PathVariable Long customerId, @Valid @RequestBody CustomerRequest customer) {
		return customerService.updateCustomer(customerId, customer)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@DeleteMapping(value = "/customers/{customerId}")
	@Operation(description = "Eliminar cliente", summary = "Eliminar cliente")
	public void deleteCustomer(@PathVariable Long customerId) {
		customerService.deleteCustomerById(customerId);
	}

}