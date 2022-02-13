package com.revature.projects.repositories;

import com.revature.projects.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerCustomRepository {
}
