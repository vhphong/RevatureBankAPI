package com.revature.projects.RevatureBankAPI.services;

import java.util.List;

import com.revature.projects.RevatureBankAPI.models.Customer;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

    List<Customer> findAllCustomers();

    Customer findCustomerById(Long id);

    Customer insertCustomer(Customer c);

    boolean modifyCustomer(Customer c);

    boolean removeCustomer(Long id);

}