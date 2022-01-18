package com.revature.projects.RevatureBankAPI.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.revature.projects.RevatureBankAPI.model.Customer;

@DataJpaTest
public class CustomerRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	public void testCreateCustomer() {

		Customer customer = new Customer("TestName", "testemail@email.com", "testpassword");
		Customer savedCustomer = customerRepository.save(customer);
		Customer expectedCustomer = entityManager.find(Customer.class, savedCustomer.getId());

		assertNotNull(expectedCustomer);

	}

	@Test
	public void testFindCustomerByCustomerId() {

		Customer customer = new Customer("TestName", "testemail@email.com", "testpassword");
		Customer savedCustomer = customerRepository.save(customer);
		Boolean isExisted = customerRepository.existsById(savedCustomer.getId());

		assertThat(isExisted).isEqualTo(true);
	}
}