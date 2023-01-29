package com.revature.projects.repositories;

import com.revature.projects.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

// @Repository   : no need @Repository annotation because JpaRepository already defined @Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerCustomRepository {
}
