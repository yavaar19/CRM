package com.yavaar.nosi.crm.integration;


import com.yavaar.nosi.crm.entity.*;
import com.yavaar.nosi.crm.entity.Order;
import com.yavaar.nosi.crm.exception.OrderDetailNotFoundException;
import com.yavaar.nosi.crm.service.OrderDetailService;
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
class OrderDetailServiceTest {

    @Value("${sql.script.delete.order}")
    private String SQLDELETECUSTOMERORDER;

    @Value("${sql.script.delete.customer}")
    private String SQLDELETECUSTOMER;

    @Value("${sql.script.delete.product}")
    private String SQLDELETEPRODUCT;

    @Value("${sql.script.delete.orderdetails}")
    private String SQLDELETEORDERDETAILS;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private OrderDetailService orderDetailService;

    Order order;
    Product product1;
    Product product2;
    Customer customer;
    OrderDetail orderDetail1;

    @BeforeEach
    void setUp() {

        product1 = new Product("SKU-758", "IPhone", "SmartPhone", new BigDecimal("100.00"), Status.ACTIVE);
        product2 = new Product("SKU-824", "Keyboard", "Wireless", new BigDecimal("100.00"), Status.ACTIVE);

        order = new Order(LocalDate.of(2023, 04, 03), new BigDecimal("200.00"), new BigDecimal("40.00"), new BigDecimal("240"), PaymentType.CASH);
        customer = new Customer("John", "Doe", "john@gmail.com", LocalDate.of(1987, 9, 3));

        customer.addOrder(order);

        orderDetail1 = new OrderDetail(product1, 1, product1.getPrice());

    }

    @Test
    void canSaveOrderDetail() {

        OrderDetail orderDetail1 = new OrderDetail(product1, 1, product1.getPrice());

        order.addOrderDetail(orderDetail1);
        product1.addOrderProduct(orderDetail1);

        OrderDetail savedOrderDetail = orderDetailService.saveOrderDetail(orderDetail1);
        OrderDetail foundOrderDetail = orderDetailService.findOrderDetailById(savedOrderDetail.getId()).get();

        assertEquals(savedOrderDetail, foundOrderDetail);

    }

    @Test
    void canSaveAllOrderDetails() {


        OrderDetail orderDetail2 = new OrderDetail(product2, 1, product2.getPrice());

        order.addOrderDetail(orderDetail1);
        order.addOrderDetail(orderDetail2);
        product1.addOrderProduct(orderDetail1);
        product2.addOrderProduct(orderDetail2);

        orderDetailService.saveAllOrderDetails(List.of(orderDetail1, orderDetail2));
        List<OrderDetail> foundOrderDetails = orderDetailService.findAllOrderDetails();

        assertEquals(2, foundOrderDetails.size());

    }

    @Test
    void canFindOrderDetail() {

        order.addOrderDetail(orderDetail1);
        product1.addOrderProduct(orderDetail1);

        OrderDetail saveOrderDetail = orderDetailService.saveOrderDetail(orderDetail1);
        OrderDetail foundOrderDetails = orderDetailService.findOrderDetailById(saveOrderDetail.getId()).get();

        assertEquals(saveOrderDetail, foundOrderDetails);

    }

    @Test
    void canUpdateOrderDetail() {

        order.addOrderDetail(orderDetail1);
        product1.addOrderProduct(orderDetail1);

        OrderDetail saveOrderDetail = orderDetailService.saveOrderDetail(orderDetail1);
        OrderDetail foundOrderDetails = orderDetailService.findOrderDetailById(saveOrderDetail.getId()).get();

        foundOrderDetails.setQuantity(26);

        orderDetailService.updateOrderDetail(foundOrderDetails);

        OrderDetail updatedOrder = orderDetailService.findOrderDetailById(saveOrderDetail.getId()).get();

        assertEquals(foundOrderDetails.getQuantity(), updatedOrder.getQuantity());

    }

    @Test
    void canDeleteOrderDetail() {

        order.addOrderDetail(orderDetail1);
        product1.addOrderProduct(orderDetail1);

        OrderDetail saveOrderDetail = orderDetailService.saveOrderDetail(orderDetail1);
        Optional<OrderDetail> foundOrderDetail = orderDetailService.findOrderDetailById(saveOrderDetail.getId());

        assertTrue(foundOrderDetail.isPresent());

        orderDetailService.deleteOrderDetailById(saveOrderDetail.getId());

        assertTrue(orderDetailService.checkIfOrderDetailIsNull(saveOrderDetail.getId()));

    }

    @Test
    void isOrderDetailNullCheck() {

        order.addOrderDetail(orderDetail1);
        product1.addOrderProduct(orderDetail1);

        OrderDetail saveOrderDetail = orderDetailService.saveOrderDetail(orderDetail1);

        assertFalse(orderDetailService.checkIfOrderDetailIsNull(saveOrderDetail.getId()));
        assertTrue(orderDetailService.checkIfOrderDetailIsNull(0));

    }

    @Test
    void orderDetailNotFoundException() {

        Exception exception = assertThrows(OrderDetailNotFoundException.class, () -> {

            orderDetailService.findOrderDetailById(100);

        });

        assertEquals("Order detail does not exist!", exception.getMessage());

    }

    @AfterEach
    void cleanUpDatabase() {

        jdbc.execute(SQLDELETEORDERDETAILS);
        jdbc.execute(SQLDELETEPRODUCT);
        jdbc.execute(SQLDELETECUSTOMERORDER);
        jdbc.execute(SQLDELETECUSTOMER);

    }

}
