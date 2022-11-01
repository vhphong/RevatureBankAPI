package com.revature.projects.controllers;

import com.revature.projects.models.Customer;
import com.revature.projects.repositories.CustomerRepository;
import com.revature.projects.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/RevBankAPI/v2/")
public class CustomerController {

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    public CustomerController(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }

    // to create customer
    // http://localhost:8080/RevBankAPI/v2/customers/create
    /*
     * Body's JSON:
       {
            "customerName": "Phong",
            "customerEmail": "phong@revbank.com",
            "customerDob": "1999-12-31",
            "customerMobile": "1-800-fakemobile",
            "customerAddress": "222 A St",
            "customerPassword": "phong1"
        }
     */
    @PostMapping("customers/create")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        if (customer != null) {
            return new ResponseEntity<Customer>(customerService.insertCustomer(customer), HttpStatus.CREATED);
        } else {
            // throw new BadRequestException("Request body does not contain customer data");
            return null;
            // return ResponseEntity.noContent().build();
        }
    }


    // get all customers
    // http://localhost:8080/RevBankAPI/v2/customers/all
    @GetMapping("customers/all")
    public List<Customer> getAllCustomers() {
        return customerService.listAllCustomers();
    }


    // get a customer by customer Id
    // http://localhost:8080/RevBankAPI/v2/customers/id/1
    @GetMapping("/customers/id/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long cId) {
        return new ResponseEntity<Customer>(customerService.listCustomerById(cId), HttpStatus.OK);
    }


    // get customers by customer name
    // http://localhost:8080/RevBankAPI/v2/customers/name/Phong
    @GetMapping("customers/name/{cname}")
    public List<Customer> getCustomerByName(@PathVariable("cname") String custName) {
        return customerService.listAllCustomersByName(custName);
    }


    // get a customer by customer email
    // http://localhost:8080/RevBankAPI/v2/customers/email/phong@revbank.com
    @GetMapping("customers/email/{cemail}")
    public List<Customer> getCustomerByEmail(@PathVariable("cemail") String ctmrEmail) {
        return customerService.listAllCustomersByEmail(ctmrEmail);
    }


    // get a customer by customer name and email
    // http://localhost:8080/RevBankAPI/v2/customers/name/Phong/email/phong@email.com
    @GetMapping("customers/name/{cname}/email/{cemail}")
    public List<Customer> getCustomerByNameAndEmail(@PathVariable("cname") String custName,
                                                    @PathVariable("cemail") String custEmail) {
        return customerService.retrieveCustomerByNameAndEmail(custName, custEmail);
    }


    // update a customer
    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long cId,
                                                   @RequestBody Customer customer) {
        return new ResponseEntity<Customer>(customerService.modifyCustomer(customer, cId), HttpStatus.OK);
    }


    // delete a customer
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") long cId) {
        if (cId > 0) {
            Boolean isDeleted = customerService.removeCustomer(cId);

            if (isDeleted) {
                return new ResponseEntity<String>("Customer id: " + cId + " deleted successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Customer id: " + cId + " was not found to delete.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<String>("Customer id: " + cId + " is invalid.", HttpStatus.NOT_FOUND);
        }
    }


    // check an email if it is taken
    @GetMapping("/existed_email/{emailparam}")
    public Boolean checkEmailIsTaken(@PathVariable("emailparam") String email) {
        return customerService.checkEmailIfTaken(email);
    }
}