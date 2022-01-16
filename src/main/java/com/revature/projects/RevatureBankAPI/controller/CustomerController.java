package com.revature.projects.RevatureBankAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.revature.projects.RevatureBankAPI.exceptions.RevBankResourceNotFoundException;
import com.revature.projects.RevatureBankAPI.model.Customer;
import com.revature.projects.RevatureBankAPI.repository.CustomerRepository;

@RestController
@RequestMapping("/RevBankAPI/v1/")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	// get all customers
	@GetMapping("customers")
	public List<Customer> getAllCustomers() {

		return this.customerRepository.findAll();

	}


	// get customer by id
	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long customerId)
			throws RevBankResourceNotFoundException {

		Customer customer = customerRepository.findById(customerId).orElseThrow(
				() -> new RevBankResourceNotFoundException("Customer with id: " + customerId + " not found"));

        return ResponseEntity.ok().body(customer);
	}


    // create a customer
    @PostMapping("customers")
    public Customer createCustomer(@RequestBody Customer customer) {

        return this.customerRepository.save(customer);

    }


}
