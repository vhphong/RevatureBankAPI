package com.revature.projects.services;

import com.revature.projects.exceptions.ResourceNotFoundException;
import com.revature.projects.models.Customer;
import com.revature.projects.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }


    @Override
    public Customer insertCustomer(Customer customer) {
//        customer.setCustomerEmail(customer.getCustomerEmail().toLowerCase());

        if (!customerRepository.findByCustomerName(customer.getCustomerName()).isEmpty()) {
            throw new ResourceNotFoundException("Customer name " + customer.getCustomerName() + " has been taken.");
        }

        if (!customerRepository.findByCustomerEmail(customer.getCustomerEmail()).isEmpty()) {
            throw new ResourceNotFoundException("Customer email " + customer.getCustomerEmail() + " has been taken.");
        }

        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> listAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer listCustomerById(long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new ResourceNotFoundException("Customer with Id: " + id + " was not found.");
        }

//        return customerRepository.findById(id).orElseThrow(
//                () -> new ResourceNotFoundException("Customer with Id: " + id + " was not found."));
    }

    @Override
    public List<Customer> listAllCustomersByName(String name) {
        List<Customer> customer = customerRepository.findByCustomerName(name);

        return customer;
    }

    @Override
    public List<Customer> listAllCustomersByEmail(String email) {
        List<Customer> customer = customerRepository.findByCustomerEmail(email);

        return customer;
    }

    @Override
    public List<Customer> retrieveCustomerByNameAndEmail(String name, String email) {
        return null;
    }

//    @Override
//    public List<Customer> retrieveCustomerByNameAndEmail(String name, String email) {
//        List<Customer> retCustomer = customerRepository.findCustomerByNameAndEmail(name, email);
//
//        return retCustomer;
//    }

    @Override
    public Customer modifyCustomer(Customer customer, long id) {

        // check whether customer with given id is existing in the database
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer with Id: " + id + " was not found."));

        if (Objects.nonNull(customer.getCustomerName()) &&
                !"".equalsIgnoreCase(customer.getCustomerName())) {
            existingCustomer.setCustomerName(customer.getCustomerName());
        }

        if (Objects.nonNull(customer.getCustomerAddress()) &&
                !"".equalsIgnoreCase(customer.getCustomerAddress())) {
            existingCustomer.setCustomerAddress(customer.getCustomerAddress());
        }

        if (Objects.nonNull(customer.getCustomerDob()) &&
                !"".equalsIgnoreCase(customer.getCustomerDob().toString())) {
            existingCustomer.setCustomerDob(customer.getCustomerDob());
        }

        if (Objects.nonNull(customer.getCustomerMobile()) &&
                !"".equalsIgnoreCase(customer.getCustomerMobile())) {
            existingCustomer.setCustomerMobile(customer.getCustomerMobile());
        }

        if (Objects.nonNull(customer.getCustomerEmail()) &&
                !"".equalsIgnoreCase(customer.getCustomerEmail())) {
            existingCustomer.setCustomerEmail(customer.getCustomerEmail());
        }

        if (Objects.nonNull(customer.getCustomerPassword()) &&
                !"".equalsIgnoreCase(customer.getCustomerPassword())) {
            existingCustomer.setCustomerPassword(customer.getCustomerPassword());
        }

        // save existingCustomer to the DB
        customerRepository.save(existingCustomer);

        return existingCustomer;
    }

    @Override
    public Customer deactivateCustomerAccount(long customerid) {
        return null;
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

    @Override
    public boolean checkEmailIfTaken(String email) {
        return customerRepository.checkExistedEmail(email.toLowerCase());
    }
}