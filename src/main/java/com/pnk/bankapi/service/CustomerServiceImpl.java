package com.pnk.bankapi.service;

import com.pnk.bankapi.dto.CustomerDTO;
import com.pnk.bankapi.exception.ResourceNotFoundException;
import com.pnk.bankapi.exception.UnsupportedMediaType;
import com.pnk.bankapi.model.Customer;
import com.pnk.bankapi.repository.CustomerRepository;
import com.pnk.bankapi.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LogManager.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Value("${server.port}")
    public int serverPort;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public String greet() {
        return "Welcome to the endpoint customers/welcome. The application is listening on PORT: " + serverPort;
    }


    @Override
    public Customer insertCustomer(Customer customerInput) {
        if (Objects.isNull(customerInput)) {
            throw new UnsupportedMediaType("Request body does not contain customer data");
        }

        if (!Utils.validateEmail(customerInput.getEmail())) {
            throw new UnsupportedMediaType("Customer email " + customerInput.getEmail() + " is invalid.");
        }

        if (!customerRepository.findByEmail(customerInput.getEmail()).isEmpty() ||
                !customerRepository.findByEmail(customerInput.getEmail().toLowerCase()).isEmpty()) {
            throw new UnsupportedMediaType("Customer email " + customerInput.getEmail() + " has been taken.");
        }

        customerInput.setEmail(customerInput.getEmail().toLowerCase());
        customerInput.setCreatedDate(LocalDateTime.now());

        if ((customerInput.getPassword() == null) || (customerInput.getPassword().isEmpty())) {
            customerInput.setPassword(Utils.generateRandomString(8));
        }

        return customerRepository.save(customerInput);
    }


    @Override
    public List<Customer> listAllCustomers() {
        return customerRepository.findAll();
    }


    @Override
    public List<Customer> listCustomerById(Long custId) {
        Optional<Customer> customer = customerRepository.findById(custId);

        List<Customer> customerList = new ArrayList<>();

        if (customer.isPresent()) {
            customerList.add(customer.get());
        } else {
            throw new ResourceNotFoundException("Customer with Id: " + custId + " is not found.");
        }

        return customerList;

//        return customerRepository.findById(id).orElseThrow(
//                () -> new ResourceNotFoundException("Customer with Id: " + id + " is not found."));
    }


    @Override
    public List<Customer> listAllCustomersByName(String name) {
        return customerRepository.findByNameContaining(name);
    }


    @Override
    public List<Customer> listAllCustomersByEmail(String email) {
        return customerRepository.findByEmail(email);
    }


    @Override
    public List<Customer> listCustomerByNameAndEmail(String name, String email) {
        return customerRepository.findByNameAndEmail(name, email);
    }


    @Override
    public Customer modifyCustomer(CustomerDTO customerDTO, Long custId) {
        // check whether customer with given id is existing in the database
        Customer existingCustomer = customerRepository.findById(custId).orElseThrow(
                () -> new ResourceNotFoundException("Customer with Id: " + custId + " is not found."));

        if (Objects.nonNull(customerDTO.getName()) && !customerDTO.getName().isEmpty()) {
            existingCustomer.setName(customerDTO.getName());
        }

        if (Objects.nonNull(customerDTO.getAddress()) && !customerDTO.getAddress().isEmpty()) {
            existingCustomer.setAddress(customerDTO.getAddress());
        }

        if (Objects.nonNull(customerDTO.getDob()) && !customerDTO.getDob().toString().isEmpty()) {
            existingCustomer.setDob(customerDTO.getDob());
        }

        if (Objects.nonNull(customerDTO.getPhone()) && !customerDTO.getPhone().isEmpty()) {
            existingCustomer.setPhone(customerDTO.getPhone());
        }

        if (Objects.nonNull(customerDTO.getEmail()) && !customerDTO.getEmail().isEmpty()) {
            existingCustomer.setEmail(customerDTO.getEmail());
        }

        if (Objects.nonNull(customerDTO.getPassword()) && !customerDTO.getPassword().isEmpty()) {
            existingCustomer.setPassword(customerDTO.getPassword());
        }

        customerDTO.setLastUpdatedDate(LocalDateTime.now());

        // save existingCustomer to the DB
        customerRepository.save(existingCustomer);

        return existingCustomer;
    }


    @Override
    public boolean removeCustomer(Long customerid) {
        try {
            customerRepository.deleteById(customerid);
            return !customerRepository.existsById(customerid);
        } catch (Exception error) {
            logger.error(error.getMessage());
            return false;
        }
    }


    @Override
    public boolean verifyEmailIfTaken(String ctmrEmail) {
        return !customerRepository.findByEmail(ctmrEmail.toLowerCase()).isEmpty();
    }


    @Override
    public boolean verifyPhoneIfTaken(String ctmrPhone) {
        return !customerRepository.findByPhone(ctmrPhone.toLowerCase()).isEmpty();
    }


    @Override
    public boolean verifyCustomerExisting(Long custId) {
        Optional<Customer> ret = customerRepository.findById(custId);

        return ret.isPresent();
    }


    @Override
    public String resetPassword(Long customerIdInput) {
        return Utils.generateRandomString(8);
    }


    @Override
    public boolean changePassword(Long customerIdInput, String newPassword) {
        Customer retrievedCustomer = customerRepository.findById(customerIdInput).orElseThrow(
                () -> new ResourceNotFoundException("Customer with Id: " + customerIdInput + " is not found."));

        retrievedCustomer.setPassword(newPassword);
        retrievedCustomer.setLastUpdatedDate(LocalDateTime.now());
        customerRepository.save(retrievedCustomer);

        return Objects.equals(newPassword, retrievedCustomer.getPassword());
    }
}