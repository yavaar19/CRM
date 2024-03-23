package com.yavaar.nosi.crm.integration;

import com.yavaar.nosi.crm.entity.Address;
import com.yavaar.nosi.crm.entity.Customer;
import com.yavaar.nosi.crm.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
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
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    private JdbcTemplate jdbc;

    @Value("${sql.script.delete.customer}")
    private String SQLDELETECUSTOMER;

    @Value("${sql.script.delete.address}")
    private String SQLDELETEADDRESS;

    @Value("${sql.script.delete.customer_address}")
    private String SQLDELETECUSTOMERADDRESS;

    @BeforeEach
    void setUp() {

        jdbc.execute("INSERT INTO CUSTOMER(ID, FIRST_NAME, LAST_NAME, EMAIL, DATE_OF_BIRTH)"
                + "VALUES(1, 'Nuzhah', 'Peerally', 'nuzhah@gmail.com', '1990-10-17')");

    }

    @AfterEach
    void cleanUpDatabase() {

        jdbc.execute(SQLDELETECUSTOMERADDRESS);
        jdbc.execute(SQLDELETECUSTOMER);
        jdbc.execute(SQLDELETEADDRESS);

    }

    @Test
    void canSaveCustomer() {

        Customer customer = new Customer("Nuzhah", "Peerally","nuzhah@gmail.com" , LocalDate.of(1990, 10, 17));

        Customer savedCustomer = customerService.saveCustomer(customer);

        assertTrue(savedCustomer.getId() > 0);
        assertEquals(customer.getId(), savedCustomer.getId());

    }

    @Test
    void canFindCustomer() {

        Optional<Customer> foundCustomer = customerService.findCustomerById(1);

        assertTrue(foundCustomer.isPresent());

    }

    @Test
    void canFindAllCustomers() {

        jdbc.execute("INSERT INTO CUSTOMER(ID, FIRST_NAME, LAST_NAME, EMAIL, DATE_OF_BIRTH)"
                + "VALUES(2, 'Yavaar', 'Nosimohomed', 'yavaar@gmail.com', '1912-11-11')");
        jdbc.execute("INSERT INTO CUSTOMER(ID, FIRST_NAME, LAST_NAME, EMAIL, DATE_OF_BIRTH)"
                + "VALUES(3, 'Varun', 'Pandit', 'varun@gmail.com', '19952-03-10')");
        jdbc.execute("INSERT INTO CUSTOMER(ID, FIRST_NAME, LAST_NAME, EMAIL, DATE_OF_BIRTH)"
                + "VALUES(4, 'Shahaad', 'Nosi', 'shahaad@gmail.com', '1990-05-03')");

        List<Customer> customers = customerService.findAllCustomers();

        assertEquals(4, customers.size());

    }

    @Test
    void canUpdateCustomer() {

        Optional<Customer> foundCustomer = customerService.findCustomerById(1);

        foundCustomer.get().setLastName("Nosimohomed");

        customerService.updateCustomer(foundCustomer.get());

        Optional<Customer> updatedCustomer = customerService.findCustomerById(1);

        assertEquals("Nosimohomed", updatedCustomer.get().getLastName());

    }

    @Test
    void canDeleteCustomer() {

        assertTrue(customerService.findCustomerById(1).isPresent());

        customerService.deleteCustomerById(1);

        assertFalse(customerService.findCustomerById(1).isPresent());

    }

    @Test
    void canFindCustomerByIdJoinFetchAddress() {

        Address address = new Address(58, "Windsor Road", "Etobicoke", "ON", "M9R-3G5");
        Customer customer = new Customer("Nuzhah", "Peerally","nuzhah@gmail.com" , LocalDate.of(1990, 10, 17));

        customer.addAddress(address);
        Customer savedCustomer = customerService.saveCustomer(customer);

        Customer foundCustomer = customerService.findCustomerByIdJoinFetchAddress(savedCustomer.getId());

        assertFalse(foundCustomer.getAddresses().isEmpty());

    }

    @Test
    void isCustomerNullCheck() {

        assertFalse(customerService.checkIfStudentIsNull(1));
        assertTrue(customerService.checkIfStudentIsNull(0));

    }

}
