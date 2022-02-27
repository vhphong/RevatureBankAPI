package com.revature.projects.services;

import com.revature.projects.models.Customer;

import java.util.List;

public interface CustomerService {

    Customer insertCustomer(Customer customer);

    List<Customer> listAllCustomers();

    Customer listCustomerById(long id);

    List<Customer> listAllCustomersByName(String name);

    List<Customer> retrieveCustomerByNameAndEmail(String name, String email);

    Customer modifyCustomer(Customer customer, long id);

    boolean removeCustomer(long id);
}
