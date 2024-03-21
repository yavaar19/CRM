package com.yavaar.nosi.crm.integration;

import com.yavaar.nosi.crm.entity.Customer;
import com.yavaar.nosi.crm.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    private JdbcTemplate jdbc;

    @Value("${sql.script.delete.customer}")
    private String sqlDeleteCustomer;
    private Customer customer;

    @BeforeEach
    public void setUp() {

        jdbc.execute(sqlDeleteCustomer);

        customer = new Customer("Nuzhah", "Peerally","nuzhah@gmail.com" , LocalDate.of(1990, 10, 17));

    }

    @Test
    public void canSaveCustomer() {

        Customer savedCustomer = customerService.saveCustomer(customer);

        assertTrue(savedCustomer.getId() > 0);
        assertEquals(customer.getId(), savedCustomer.getId());

    }

    @Test
    public void canFindCustomer() {

        Customer savedCustomer = customerService.saveCustomer(customer);

        Customer foundCustomer = customerService.findCustomerById(savedCustomer.getId()).get();

        assertEquals(savedCustomer.getId(), foundCustomer.getId());

    }

    @Test
    public void canFindAllCustomers() {

        Customer customer1 = new Customer("Yavaar", "Nosimohomed","yavaar@gmail.com" , LocalDate.of(1990, 10, 17));
        Customer customer2 = new Customer("Shahaad", "Nosimohomed","shahaad@gmail.com" , LocalDate.of(1990, 10, 17));
        Customer customer3 = new Customer("Fazilet", "Nosimohomed","fazilet@gmail.com" , LocalDate.of(1990, 10, 17));

        customerService.saveCustomer(customer);
        customerService.saveCustomer(customer1);
        customerService.saveCustomer(customer2);
        customerService.saveCustomer(customer3);

        List<Customer> customers = customerService.findAllCustomers();

        assertEquals(4, customers.size());

    }

    @Test
    public void canUpdateCustomer() {

        customerService.saveCustomer(customer);

        Customer foundCustomer = customerService.findCustomerById(customer.getId()).get();

        customer.setFirstName("Yavaar");
        customer.setLastName("Nosimohomed");

        customerService.updateCustomer(customer);

        Customer foundUpdatedCustomer = customerService.findCustomerById(customer.getId()).get();

        assertNotEquals(foundCustomer.getFirstName(), foundUpdatedCustomer.getFirstName());
        assertNotEquals(foundCustomer.getLastName(), foundUpdatedCustomer.getLastName());

    }

    @Test
    public void canDeleteCustomer() {

        customerService.saveCustomer(customer);

        Customer foundCustomer = customerService.findCustomerById(customer.getId()).get();

        assertEquals(customer, foundCustomer);

        customerService.deleteCustomerById(customer.getId());

        Optional<Customer> deletedCustomer = customerService.findCustomerById(customer.getId());

        assertTrue(deletedCustomer.isEmpty());

    }

}
