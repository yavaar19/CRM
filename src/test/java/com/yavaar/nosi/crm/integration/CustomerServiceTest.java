package com.yavaar.nosi.crm.integration;

import com.yavaar.nosi.crm.entity.Address;
import com.yavaar.nosi.crm.entity.Customer;
import com.yavaar.nosi.crm.entity.Order;
import com.yavaar.nosi.crm.entity.PaymentType;
import com.yavaar.nosi.crm.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
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

    @Value("${sql.script.delete.order}")
    private String SQLDELETECUSTOMERORDER;

    @BeforeEach
    void setUp() {

        jdbc.execute("INSERT INTO CUSTOMER(ID, FIRST_NAME, LAST_NAME, EMAIL, DATE_OF_BIRTH)"
                + "VALUES(1, 'John', 'Doe', 'john@gmail.com', '1987-09-03')");

    }

    @AfterEach
    void cleanUpDatabase() {

        jdbc.execute(SQLDELETECUSTOMERADDRESS);
        jdbc.execute(SQLDELETECUSTOMERORDER);
        jdbc.execute(SQLDELETECUSTOMER);
        jdbc.execute(SQLDELETEADDRESS);

    }

    @Test
    void canSaveCustomer() {

        Customer customer = new Customer("John", "Doe","john@gmail.com" , LocalDate.of(1987, 9, 3));

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
                + "VALUES(2, 'Will', 'Smith', 'smith@gmailgmail.com', '1912-11-11')");
        jdbc.execute("INSERT INTO CUSTOMER(ID, FIRST_NAME, LAST_NAME, EMAIL, DATE_OF_BIRTH)"
                + "VALUES(3, 'Varun', 'Pandit', 'varun@gmail.com', '19952-03-10')");
        jdbc.execute("INSERT INTO CUSTOMER(ID, FIRST_NAME, LAST_NAME, EMAIL, DATE_OF_BIRTH)"
                + "VALUES(4, 'Azhar', 'Nosi', 'azhar@gmail.com', '1990-05-03')");

        List<Customer> customers = customerService.findAllCustomers();

        assertEquals(4, customers.size());

    }

    @Test
    void canUpdateCustomer() {

        Optional<Customer> foundCustomer = customerService.findCustomerById(1);

        foundCustomer.get().setLastName("Smith");

        customerService.updateCustomer(foundCustomer.get());

        Optional<Customer> updatedCustomer = customerService.findCustomerById(1);

        assertEquals("Smith", updatedCustomer.get().getLastName());

    }

    @Test
    void canDeleteCustomer() {

        assertTrue(customerService.findCustomerById(1).isPresent());

        customerService.deleteCustomerById(1);

        assertFalse(customerService.findCustomerById(1).isPresent());

    }

    @Test
    void canFindCustomerByIdJoinFetchAddress() {

        Address address = new Address(58, "Luka Road", "Bradford", "ON", "L4R-S5G");
        Customer customer = new Customer("John", "Doe","john@gmail.com" , LocalDate.of(1990, 10, 17));

        customer.addAddress(address);
        Customer savedCustomer = customerService.saveCustomer(customer);

        Customer foundCustomer = customerService.findCustomerByIdJoinFetchAddress(savedCustomer.getId());

        assertFalse(foundCustomer.getAddresses().isEmpty());

    }

    @Test
    void canFindCustomerByIdJoinFetchOrder() {

        Order order1 = new Order(LocalDate.of(2023, 2, 5), new BigDecimal("200.00"), new BigDecimal("40.00"), new BigDecimal("240.00"), PaymentType.MASTERCARD);
        Order order2 = new Order(LocalDate.of(2021, 8, 25), new BigDecimal("100.00"), new BigDecimal("20.00"), new BigDecimal("120.00"), PaymentType.AMERICAN_EXPRESS);
        Customer customer = new Customer("John", "Doe","john@gmail.com" , LocalDate.of(1990, 10, 17));

        customer.addOrder(order1);
        customer.addOrder(order2);

        Customer savedCustomer = customerService.saveCustomer(customer);

        Customer foundCustomer = customerService.findCustomerByIdJoinFetchOrder(savedCustomer.getId());

        System.out.println(foundCustomer.getOrders());

        assertFalse(foundCustomer.getOrders().isEmpty());

    }

    @Test
    void isCustomerNullCheck() {

        assertFalse(customerService.checkIfCustomerIsNull(1));
        assertTrue(customerService.checkIfCustomerIsNull(0));

    }

}
