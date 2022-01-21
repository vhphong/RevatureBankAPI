package com.revature.projects.controllers;

import com.revature.projects.models.Customer;
import com.revature.projects.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/RevBankAPI/v2/")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        super();
        this.customerService = customerService;
    }

    // build create employee REST API
    /*
     * Body 's JSON: { "name": "Phong", "email": "phong@revbank.com", "dob":
     * "2001-08-18", "mobile": "123456789", "address": "111 A St", "password":
     * "phong1" }
     */
    @PostMapping("customers")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<Customer>(customerService.insertCustomer(customer), HttpStatus.CREATED);
    }

    // build get all customers REST API
    @GetMapping("customers")
    public List<Customer> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    // build get a customer by Id REST API
    // http://localhost:8080/RevBankAPI/v2/customers/1
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long cId) {
        return new ResponseEntity<Customer>(customerService.findCustomerById(cId), HttpStatus.OK);
    }

    // build update customer REST API
    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long cid,
                                                   @RequestBody Customer customer) {
        return new ResponseEntity<Customer>(customerService.modifyCustomer(customer, cid), HttpStatus.OK);
    }

    // build delete customer REST API
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") long id) {
        customerService.removeCustomer(id);

        return new ResponseEntity<String>("Employee deleted successfully!", HttpStatus.OK);
    }



}
