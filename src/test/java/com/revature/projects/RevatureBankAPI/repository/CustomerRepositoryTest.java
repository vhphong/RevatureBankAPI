package com.revature.projects.RevatureBankAPI.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

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

	@Test
	public void testFindCustomerByCustomerIdNotExisted() {

		Customer customer = new Customer("TestName", "testemail@email.com", "testpassword");
		Customer savedCustomer = customerRepository.save(customer);
		Customer expectedCustomer = entityManager.find(Customer.class, Long.valueOf(999999999));

		assertNull(expectedCustomer);

	}

	@Test
	@Rollback(value = false)
	public void testUpdateCustomer() {

		Customer customer = new Customer("TestName", "testemail@email.com", "testpassword");
		Customer savedCustomer = customerRepository.save(customer);

		Customer expectedCustomer = entityManager.find(Customer.class, savedCustomer.getId());
		expectedCustomer.setPassword("newpassword");

		assertThat(expectedCustomer.getPassword()).isEqualTo("newpassword");

	}

	@Test
	public void testGetAllCustomers() {

		Customer customer = new Customer("TestName", "testemail@email.com", "testpassword");
		customerRepository.save(customer);

		List<Customer> customers = customerRepository.findAll();

		assertThat(customers).size().isGreaterThan(0);

	}

	@Test
	@Rollback(value = false)
	public void testDeleteCustomer() {

		Customer customer = new Customer("TestName", "testemail@email.com", "testpassword");
		customerRepository.save(customer);

		Long custid = customer.getId();

		boolean isExistedBeforeDelete = customerRepository.findById(custid).isPresent();

		customerRepository.deleteById(custid);

		boolean isExistedAfterDelete = customerRepository.findById(custid).isPresent();

		assertTrue(isExistedBeforeDelete);
		assertFalse(isExistedAfterDelete);

	}

}