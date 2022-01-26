package com.revature.projects.repositories;

import com.revature.projects.models.Customer;

import java.util.List;

public interface CustomerCustomRepository {

    List<Customer> findCustomerByName(String name);

}
