package com.pnk.bankapi.controller;

import com.pnk.bankapi.dto.CustomerDTO;
import com.pnk.bankapi.exception.ResourceNotFoundException;
import com.pnk.bankapi.model.Customer;
import com.pnk.bankapi.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


//@CrossOrigin(origins = {"http://trusted-domain.com", "https://trusted-domain.com"})
@CrossOrigin("*")   // *: all clients are able to call the CustomerController endpoint
@RestController
@RequestMapping("/RevBankAPI/v2/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/welcome")
    public String welcomeCustomers() {
        return customerService.greet();
    }


    // to create customer
    // http://localhost:8081/RevBankAPI/v2/customers/create
    /*
     * Body's JSON:
        {
            "name": "John",
            "email": "John@example.com",
            "dob": "2001-12-22",
            "phone": "1-800-fake",
            "address": "2023 Apple St",
            "password": "faKePw1",
            "createdDate": "",
            "lastUpdatedDate": ""
        }

            should return

        {
            "customerId": 2,
            "name": "Jane",
            "email": "jane@test.com",
            "dob": "2002-12-16T00:00:00.000+00:00",
            "phone": "1-800-fake",
            "address": "USA",
            "password": "faKePw2",
            "createdDate": "2023-09-09T19:39:31.362+00:00",
            "lastUpdatedDate": "2023-09-09T19:39:31.362+00:00",
            "accountList": []
        }
     */
    @PostMapping("/create")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        Customer persistedCustomer = customerService.insertCustomer(customer);
        URI uri = URI.create("/customers/" + persistedCustomer.getCustomerId());

        return ResponseEntity.created(uri).body(persistedCustomer);
    }


    // http://localhost:8081/RevBankAPI/v2/customers?all
    // http://localhost:8081/RevBankAPI/v2/customers?id=1
    // http://localhost:8081/RevBankAPI/v2/customers?name=John
    // http://localhost:8081/RevBankAPI/v2/customers?email=john@email.com
    @GetMapping("")
    public ResponseEntity<List<Customer>> getFilteredCustomers(
            @RequestParam(value = "all", required = false) String allCustomers,
            @RequestParam(value = "id", required = false) Long ctmrId,
            @RequestParam(value = "name", required = false) String ctmrName,
            @RequestParam(value = "email", required = false) String ctmrEmail) {

        try {
            if (allCustomers != null) {
                return ResponseEntity.ok(customerService.listAllCustomers());
            } else if (ctmrId != null) {
                return ResponseEntity.ok(customerService.listCustomerById(ctmrId));
            } else if (ctmrName != null && ctmrEmail != null) {
                return ResponseEntity.ok(customerService.listCustomerByNameAndEmail(ctmrName, ctmrEmail));
            } else if (ctmrName != null) {
                return ResponseEntity.ok(customerService.listAllCustomersByName(ctmrName));
            } else if (ctmrEmail != null) {
                return ResponseEntity.ok(customerService.listAllCustomersByEmail(ctmrEmail));
            } else {
                return ResponseEntity.notFound().build(); // Default to not found
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    // update a customer
    @PatchMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long cId, @RequestBody CustomerDTO customerDTO) {
        try {
            return new ResponseEntity<>(customerService.modifyCustomer(customerDTO, cId), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    // delete a customer
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long cId) {
        boolean isDeleted = customerService.removeCustomer(cId);
        if (isDeleted) {
            return new ResponseEntity<>("Customer id: " + cId + " deleted successfully.", HttpStatus.OK);
        }

        return new ResponseEntity<>("Customer id: " + cId + " is not found to delete.", HttpStatus.NOT_FOUND);
    }


    // check an email if it is taken
    @GetMapping("/existing_email/{emailParam}")
    public Boolean confirmEmailIsTaken(@PathVariable("emailParam") String email) {
        return customerService.verifyEmailIfTaken(email);
    }
}