package com.yavaar.nosi.crm.integration;

import com.yavaar.nosi.crm.entity.Customer;
import com.yavaar.nosi.crm.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    public void canAddCustomer() {

        Customer customer = new Customer("Nuzhah", "Peerally","nuzhah@gmail.com" , LocalDate.of(1990, 10, 17));
        Customer savedCustomer = customerService.save(customer);

        assertTrue(savedCustomer.getId() > 0);
        assertEquals(customer.getId(), savedCustomer.getId());

    }

    @Test
    public void canFindCustomer() {

        Customer customer = new Customer("Nuzhah", "Peerally","nuzhah@gmail.com" , LocalDate.of(1990, 10, 17));
        Customer savedCustomer = customerService.save(customer);

        Customer foundCustomer = customerService.findById(savedCustomer.getId()).get();

        assertEquals(savedCustomer.getId(), foundCustomer.getId());

    }

    @Test
    public void canUpdateCustomer() {

        Customer customer = new Customer("Nuzhah", "Peerally","nuzhah@gmail.com" , LocalDate.of(1990, 10, 17));
        customerService.save(customer);

        Customer foundCustomer = customerService.findById(customer.getId()).get();

        customer.setFirstName("Yavaar");
        customer.setLastName("Nosimohomed");

        customerService.updateCustomer(customer);

        Customer foundUpdatedCustomer = customerService.findById(customer.getId()).get();

        assertNotEquals(foundCustomer.getFirstName(), foundUpdatedCustomer.getFirstName());
        assertNotEquals(foundCustomer.getLastName(), foundUpdatedCustomer.getLastName());

    }

    @Test
    public void canDeleteCustomer() {

        Customer customer = new Customer("Nuzhah", "Peerally","nuzhah@gmail.com" , LocalDate.of(1990, 10, 17));
        customerService.save(customer);

        Customer foundCustomer = customerService.findById(customer.getId()).get();

        assertEquals(customer, foundCustomer);

        customerService.deleteCustomer(customer);

        Optional<Customer> deletedCustomer = customerService.findById(customer.getId());

        assertTrue(deletedCustomer.isEmpty());

    }

    @Test
    public void canFindAllCustomers() {

        Customer customer = new Customer("Nuzhah", "Peerally","nuzhah@gmail.com" , LocalDate.of(1990, 10, 17));
        Customer customer1 = new Customer("Yavaar", "Nosimohomed","yavaar@gmail.com" , LocalDate.of(1990, 10, 17));
        Customer customer2 = new Customer("Shahaad", "Nosimohomed","shahaad@gmail.com" , LocalDate.of(1990, 10, 17));
        Customer customer3 = new Customer("Fazilet", "Nosimohomed","fazilet@gmail.com" , LocalDate.of(1990, 10, 17));

        customerService.save(customer);
        customerService.save(customer1);
        customerService.save(customer2);
        customerService.save(customer3);

        List<Customer> customers = customerService.findAll();

        assertEquals(4, customers.size());

    }


}
