package com.revature.projects.services;

import com.revature.projects.models.Customer;

import java.util.List;

public interface CustomerService {

    Customer insertCustomer(Customer customer);

    List<Customer> findAllCustomers();

    Customer findCustomerById(long id);

    Customer modifyCustomer(Customer customer, long id);

    boolean removeCustomer(long id);

}
