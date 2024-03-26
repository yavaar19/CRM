package com.yavaar.nosi.crm.integration;


import com.yavaar.nosi.crm.entity.Customer;
import com.yavaar.nosi.crm.entity.Order;
import com.yavaar.nosi.crm.service.CustomerService;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class OrderServiceTest {

    @Value("${sql.script.delete.order}")
    private String SQLDELETECUSTOMERORDER;

    @Value("${sql.script.delete.customer}")
    private String SQLDELETECUSTOMER;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JdbcTemplate jdbc;

    Order order;

    Customer customer;

    @BeforeEach
    void setUp() {

        customer = new Customer("Terry", "Taylor","taylor@gmail.com" , LocalDate.of(1983, 11, 1));
        order = new Order(LocalDate.of(2023, 12,11), new BigDecimal("200.00"),
                new BigDecimal("40.00"), new BigDecimal("240.00"));

    }

    @Test
    void canSaveOrder() {

        customer.addOrder(order);

        Order savedOrder = orderService.saveOrder(order);

        Order updatedOrder = orderService.findOrderById(savedOrder.getId()).get();

        assertEquals(savedOrder, updatedOrder);

    }

    @Test
    void canSaveAllOrders() {

        Order order1 = new Order(LocalDate.of(2023, 12,11), new BigDecimal("400.00"),
                new BigDecimal("80.00"), new BigDecimal("480.00"));
        Order order2 = new Order(LocalDate.of(2022, 9,01), new BigDecimal("300.00"),
                new BigDecimal("60.00"), new BigDecimal("360.00"));

        customer.addOrder(order1);
        customer.addOrder(order2);

        orderService.saveAllOrders(List.of(order1, order2));

        List<Order> foundOrder = orderService.findAllOrders();

        assertEquals(2, foundOrder.size());

    }

    @Test
    void canFindOrder() {

        customer.addOrder(order);

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

        customer.addOrder(order);
        customer.addOrder(order1);
        customer.addOrder(order2);

        orderService.saveAllOrders(List.of(order, order1, order2));

        List<Order> orders = orderService.findAllOrders();

        assertEquals(3, orders.size());

    }

    @Test
    void canUpdateOrder() {

        customer.addOrder(order);
        Order savedOrder = orderService.saveOrder(order);
        Order foundOrder = orderService.findOrderById(savedOrder.getId()).get();

        foundOrder.setTaxAmount(new BigDecimal("10.00"));

        orderService.updateOrder(foundOrder);

        Order updatedOrder = orderService.findOrderById(savedOrder.getId()).get();

        assertEquals(foundOrder.getTaxAmount(), updatedOrder.getTaxAmount());

    }

    @Test
    void canDeleteOrder() {

        customer.addOrder(order);
        Order savedOrder = orderService.saveOrder(order);

        assertTrue(orderService.findOrderById(savedOrder.getId()).isPresent());

        orderService.deleteOrderById(savedOrder.getId());

        assertFalse(orderService.findOrderById(savedOrder.getId()).isPresent());

    }

    @Test
    void canFindOrderAndCustomerEager() {

        customer.addOrder(order);
        Order savedOrder = orderService.saveOrder(order);
        Order foundOrder = orderService.findOrderById(savedOrder.getId()).get();

        assertEquals(savedOrder.getCustomer(), foundOrder.getCustomer());

    }

    @Test
    void isOrderNullCheck() {

        customer.addOrder(order);
        Order savedOrder = orderService.saveOrder(order);

        assertFalse(orderService.checkIfStudentIsNull(savedOrder.getId()));
        assertTrue(orderService.checkIfStudentIsNull(0));

    }

    @AfterEach
    void cleanUpDatabase() {

        jdbc.execute(SQLDELETECUSTOMERORDER);
        jdbc.execute(SQLDELETECUSTOMER);

    }

}
