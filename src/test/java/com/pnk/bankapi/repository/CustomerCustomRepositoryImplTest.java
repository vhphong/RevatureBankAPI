package com.pnk.bankapi.repository;

import com.pnk.bankapi.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class CustomerCustomRepositoryImplTest {

    Customer customer1;
    @Mock
    private EntityManager testEntityManager;    // TestEntityManager is for testing customized stuff
    @Mock
    private TypedQuery<Customer> query;
    @InjectMocks
    private CustomerCustomRepositoryImpl customerCustomRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this); // cause testEntityManager is null if not using

        customer1 = new Customer(1L, "testName1", "testemail1@email.com", "testPw1");
    }

    @AfterEach
    void tearDown() {
        customer1 = null;
    }

    @Test
    void testFindCustomerByName() {
        String queryingNameInput = customer1.getName();

        String sql = "SELECT c from Customer c WHERE c.customerName=:name";
        when(testEntityManager.createQuery(sql, Customer.class)).thenReturn(query);
        when(query.setParameter("name", queryingNameInput)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(customer1));

        List<Customer> result = customerCustomRepository.findCustomerByName(queryingNameInput);

        assertEquals(1, result.size());
        assertEquals(customer1, result.get(0));
        assertEquals(queryingNameInput, result.get(0).getName());

        // Verify that the EntityManager and TypedQuery were called with the correct parameters
        verify(testEntityManager).createQuery(sql, Customer.class);
        verify(query).setParameter("name", queryingNameInput);
        verify(query).getResultList();
    }
}