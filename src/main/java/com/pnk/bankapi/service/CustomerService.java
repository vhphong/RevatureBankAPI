package com.pnk.bankapi.service;

import com.pnk.bankapi.dto.CustomerDTO;
import com.pnk.bankapi.model.Customer;

import java.util.List;

public interface CustomerService {

    String greet();

    Customer insertCustomer(Customer customerInput);

    List<Customer> listAllCustomers();

    List<Customer> listCustomerById(Long custId);

    List<Customer> listAllCustomersByName(String name);

    List<Customer> listAllCustomersByEmail(String email);

    List<Customer> listCustomerByNameAndEmail(String name, String email);

    Customer modifyCustomer(CustomerDTO customerDTO, Long custId);

    boolean removeCustomer(Long custId);

    boolean verifyEmailIfTaken(String email);

    boolean verifyPhoneIfTaken(String phone);

    boolean verifyCustomerExisting(Long custId);

    String resetPassword(Long customerIdInput);

    boolean changePassword(Long customerIdInput, String newPassword);
}