package com.revature.projects.repositories;

import com.revature.projects.models.Customer;

import java.util.List;

public interface CustomerCustomRepository {

    Customer findCustomerById(Long id);

    List<Customer> findCustomersByName(String name);

    List<Customer> findCustomerByEmail(String email);

    List<Customer> findCustomerByNameAndEmail(String name, String emailAddress);

    Boolean checkExistedEmail(String email);
}
