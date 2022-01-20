package com.revature.projects.RevatureBankAPI.services;

import com.revature.projects.RevatureBankAPI.models.Customer;
import com.revature.projects.RevatureBankAPI.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public Customer findCustomerById(Long id) {
        Optional<Customer> result = customerRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    @Override
    public Customer insertCustomer(Customer c) {
        return customerRepository.save(c);
    }

    @Override
    public boolean modifyCustomer(Customer c) {
        try {
            customerRepository.save(c);

            return true;
        } catch (Exception error) {
            System.out.println(error.getMessage());

            return false;
        }
    }

    @Override
    public boolean removeCustomer(Long id) {
        try {
            customerRepository.deleteById(id);

            return true;
        } catch (Exception error) {
            System.out.println(error.getMessage());

            return false;
        }
    }

}
