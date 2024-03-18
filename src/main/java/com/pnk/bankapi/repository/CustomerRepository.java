package com.pnk.bankapi.repository;

import com.pnk.bankapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByName(String ctmrName);

    List<Customer> findByNameOrderByCustomerId(String ctmrName);

    List<Customer> findByNameIsNot(String ctmrName);

    List<Customer> findByNameStartingWith(String prefix);

    List<Customer> findByNameEndingWith(String suffix);

    List<Customer> findByNameContaining(String infix);

    List<Customer> findByEmail(String ctmrEmail);

    List<Customer> findByEmailOrderByCustomerId(String ctmrEmail);

    List<Customer> findByEmailStartingWith(String prefix);

    List<Customer> findByEmailEndingWith(String suffix);

    List<Customer> findByEmailContaining(String infix);

    List<Customer> findByNameAndEmail(String ctmrName, String ctmrEmail);

    List<Customer> findByNameOrEmail(String ctmrName, String ctmrEmail);

    List<Customer> findByPhone(String ctmrPhone);

    List<Customer> findByPhoneContaining(String infix);

    List<Customer> findByAddressContaining(String infix);

}