package com.revature.projects.repositories;

import com.revature.projects.models.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository   // no need @Repository annotation because JpaRepository already defined @Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Declare customized method(s) here
//    @Query("SELECT name FROM customer c WHERE c.name = ?1")
//    public Customer listAllCustomersByName(String name);

}
