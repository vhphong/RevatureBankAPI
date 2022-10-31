package com.revature.projects.repositories;

import com.revature.projects.models.Customer;

import java.util.List;

public interface CustomerCustomRepository {

    List<Customer> findCustomerByName(String name);

    List<Customer> findCustomerByEmail(String email);

    List<Customer> findCustomerByNameAndEmail(String name, String emailAddress);

    String greetingCustomer();

    Boolean checkExistedEmail(String email);
}
