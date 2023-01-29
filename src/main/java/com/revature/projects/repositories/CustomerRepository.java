package com.revature.projects.repositories;

import com.revature.projects.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// @Repository   : no need @Repository annotation because JpaRepository already defined @Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerCustomRepository {

    public List<Customer> findByCustomerName(String customerName);

    public List<Customer> findByCustomerEmail(String customerEmail);

//    public List<Customer> findByCustomerNameAndEmail(String customerName, String customerEmail);
}
