package com.pnk.bankapi.repository;

import com.pnk.bankapi.model.Customer;

import java.util.List;

public interface CustomerCustomRepository {

    List<Customer> findCustomerByName(String name);

}
