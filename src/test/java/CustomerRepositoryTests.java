import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.revature.projects.RevatureBankAPI.model.Customer;
import com.revature.projects.RevatureBankAPI.repository.CustomerRepository;

public class CustomerRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    // test methods placed here
    @Test
    public void testCreateCustomer() {

        Customer customer = new Customer();

        customer.setName("TestSample");
        customer.setEmail("emailsample@revaturebank.com");
        customer.setPassword("passwordsample");

        Customer savedCustomer = customerRepository.save(customer);
        Customer existedCustomer = entityManager.find(Customer.class, savedCustomer.getId());

        assertThat(customer.getEmail()).isEqualTo(existedCustomer.getEmail());

    }

}
