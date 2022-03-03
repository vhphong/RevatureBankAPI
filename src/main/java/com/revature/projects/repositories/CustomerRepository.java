package com.revature.projects.repositories;

import com.revature.projects.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerCustomRepository {
}
