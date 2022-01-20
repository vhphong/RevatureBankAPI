package com.revature.projects.RevatureBankAPI.repositories;

import com.revature.projects.RevatureBankAPI.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
