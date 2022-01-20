package com.revature.projects.RevatureBankAPI.controller;

import com.revature.projects.RevatureBankAPI.models.Customer;
import com.revature.projects.RevatureBankAPI.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:8080") // cors
@RestController
@RequestMapping("/RevBankAPI/v2/")
public class CustomerController {

    @Autowired
    CustomerService customerService;


    // Routes placed here
    public List<Customer> getAllCustomers() {

        return customerService.findAllCustomers();
    }


    public Customer getCustomerById(Long id) {
        Customer result = customerService.findCustomerById(id);

        if (result.getId() != 0) {
            return result;
        } else {
            return null;
        }
    }


    public Customer createCustomer(Customer c) {

        return  customerService.insertCustomer(c);
    }


    boolean updateCustomer(Customer c) {
        try {
            customerService.modifyCustomer(c);

            return true;
        } catch (Exception error) {
            System.out.println(error.getMessage());

            return false;
        }
    }


    boolean deleteCustomer(Long id) {
        try {
            customerService.removeCustomer(id);

            return true;
        } catch (Exception error) {
            System.out.println(error.getMessage());

            return false;
        }
    }

}
