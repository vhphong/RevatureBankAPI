package com.revature.projects.RevatureBankAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.projects.RevatureBankAPI.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Customer findByName(String name);


}
