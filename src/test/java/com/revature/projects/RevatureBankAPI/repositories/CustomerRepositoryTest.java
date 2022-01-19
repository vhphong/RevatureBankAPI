//package com.revature.projects.RevatureBankAPI.repositories;
//
//import com.revature.projects.RevatureBankAPI.models.Customer;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//public class CustomerRepositoryTest {
//
//    @Autowired
//    private TestEntityManager testEntityManager;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Test
//    public void testGetAllCustomers() {
//
//        Customer customer = new Customer("TestName", "testemail@email.com", "testpassword");
//        customerRepository.save(customer);
//
//        List<Customer> customers = (List<Customer>) customerRepository.findAll();
//
//        assertThat(customers).size().isGreaterThan(0);
//
//    }
//
//}