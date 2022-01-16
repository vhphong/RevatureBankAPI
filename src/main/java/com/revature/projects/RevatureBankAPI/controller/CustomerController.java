package com.revature.projects.RevatureBankAPI.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	/* Body 's JSON:
	* 	{
			"name": "Phong",
			"email": "phong@revbank.com",
			"dob": "2001-08-18",
			"mobile": "123456789",
			"address": "111 A St",
			"password": "phong1"
		}
	* */
    @PostMapping("customers")
    public Customer createCustomer(@RequestBody Customer customer) {

        return this.customerRepository.save(customer);

    }


    // update customer
    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Long customerId, @RequestBody Customer newerData) throws RevBankResourceNotFoundException {

        Customer customer = customerRepository.findById(customerId).
                orElseThrow(() -> new RevBankResourceNotFoundException("Customer with id: " + customerId + " not found"));

        customer.setName(newerData.getName());
        customer.setEmail(newerData.getEmail());
        customer.setDob(newerData.getDob());
        customer.setMobile(newerData.getMobile());
        customer.setAddress(newerData.getAddress());
        customer.setPassword(newerData.getPassword());

        return ResponseEntity.ok(this.customerRepository.save(customer));

    }


    // delete customer
    @DeleteMapping("/customers/{id}")
    public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long customerId) throws RevBankResourceNotFoundException {

        Customer customer = customerRepository.findById(customerId).
                orElseThrow(() -> new RevBankResourceNotFoundException("Customer with id: " + customerId + " not found"));

        this.customerRepository.delete(customer);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;

    }

}
