package com.revature.projects.services;

import com.revature.projects.exceptions.ResourceNotFoundException;
import com.revature.projects.models.Customer;
import com.revature.projects.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {


    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer insertCustomer(Customer customer) {
        return customerRepository.save(customer);

    }

    @Override
    public List<Customer> listAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer listCustomerById(long id) {
        Optional<Customer> customer = customerRepository.findById(id);

//        if (customer.isPresent()) {
//            return customer.get();
//        } else {
//            return null;
//        }

        return customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer with Id: " + id + " was not found."));
    }

    @Override
    public Customer modifyCustomer(Customer customer, long id) {

        // check whether customer with given id is existed in the database or not
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer with Id: " + id + " was not found."));

        existingCustomer.setName(customer.getName());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setDob(customer.getDob());
        existingCustomer.setMobile(customer.getMobile());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPassword(customer.getPassword());

        // save existingCustomer to the DB
        customerRepository.save(existingCustomer);

        return existingCustomer;
    }

    @Override
    public boolean removeCustomer(long id) {
        try {
            customerRepository.deleteById(id);

            return true;
        } catch (Exception error) {
            System.out.println(error.getMessage());

            return false;
        }
    }

}