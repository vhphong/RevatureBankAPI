package com.revature.projects.RevatureBankAPI.repositories;

import com.revature.projects.RevatureBankAPI.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//public interface CustomerRepository extends JpaRepository<Customer, Long> {
//}
//public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {
//}
public interface CustomerRepository extends CrudRepository<Customer, Long> {

//    List<Customer> findAll();

}
