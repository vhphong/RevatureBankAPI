package com.revature.projects.RevatureBankAPI.services;

import com.revature.projects.RevatureBankAPI.models.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAllCustomers();

    Customer findCustomerById(Long id);

    Customer insertCustomer(Customer c);

    boolean modifyCustomer(Customer c);

    boolean removeCustomer(Long id);

}
