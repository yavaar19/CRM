package com.yavaar.nosi.crm.integration;


import com.yavaar.nosi.crm.entity.Customer;
import com.yavaar.nosi.crm.entity.Order;
import com.yavaar.nosi.crm.service.OrderService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class OrderServiceTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Value("${sql.script.delete.order}")
    private String SQLDELETECUSTOMERORDER;

    @Value("${sql.script.delete.customer}")
    private String SQLDELETECUSTOMER;

    @Autowired
    private OrderService orderService;

    Order order;

    Customer customer;

    @BeforeEach
    void setUp() {

        order = new Order(LocalDate.of(2024, 01,02), new BigDecimal("100.00"),
                new BigDecimal("20.00"), new BigDecimal("120.00"));
        customer = new Customer("John", "Doe","john@gmail.com",
                LocalDate.of(1987, 9, 3));
        order.setCustomer(customer);


    }

    @Test
    void canSaveOrder() {

        Order savedOrder = orderService.saveOrder(order);

        Order foundOrder = orderService.findOrderById(savedOrder.getId()).get();

        assertEquals(savedOrder, foundOrder);

    }

    @Test
    void canFindOrder() {

        Order savedOrder = orderService.saveOrder(order);

        Order foundOrder = orderService.findOrderById(savedOrder.getId()).get();

        assertEquals(savedOrder, foundOrder);

    }

    @Test
    void canFindAllOrders() {

        Order order1 = new Order(LocalDate.of(2023, 12,11), new BigDecimal("200.00"),
                new BigDecimal("40.00"), new BigDecimal("240.00"));
        Order order2 = new Order(LocalDate.of(2022, 9,01), new BigDecimal("300.00"),
                new BigDecimal("60.00"), new BigDecimal("360.00"));
        order1.setCustomer(customer);
        order2.setCustomer(customer);

        orderService.saveOrder(order);
        orderService.saveOrder(order1);
        orderService.saveOrder(order2);

        List<Order> orders = orderService.findAllOrders();

        assertEquals(3, orders.size());

    }

    @AfterEach
    void cleanUpDatabase() {

//        jdbc.execute(SQLDELETECUSTOMERORDER);
//        jdbc.execute(SQLDELETECUSTOMER);

    }

}
