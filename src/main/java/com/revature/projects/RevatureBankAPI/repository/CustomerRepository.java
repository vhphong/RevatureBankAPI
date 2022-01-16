package com.revature.projects.RevatureBankAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.projects.RevatureBankAPI.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
