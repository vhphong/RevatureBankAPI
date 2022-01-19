package com.revature.projects.RevatureBankAPI.controllers;

import com.revature.projects.RevatureBankAPI.models.Customer;
import com.revature.projects.RevatureBankAPI.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080") // cors
@Controller
@RestController
@RequestMapping("/RevBankAPI/v2/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // get all customers
    @GetMapping("customers")
    public List<Customer> getAllCustomers() {
        return customerService.findAllCustomers();
    }

}
