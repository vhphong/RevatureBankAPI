package com.revature.projects.RevatureBankAPI.services;

import com.revature.projects.RevatureBankAPI.models.Customer;
import com.revature.projects.RevatureBankAPI.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public List<Customer> findAllCustomers() {
        return null;
    }

    @Override
    public Customer findCustomerById(Long id) {
        return null;
    }

    @Override
    public Customer insertCustomer(Customer c) {
        return null;
    }

    @Override
    public boolean modifyCustomer(Customer c) {
        return false;
    }

    @Override
    public boolean removeCustomer(Long id) {
        return false;
    }
}
