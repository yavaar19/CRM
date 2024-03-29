package com.yavaar.nosi.crm.unit;


import com.yavaar.nosi.crm.dao.OrderDao;
import com.yavaar.nosi.crm.entity.Order;
import com.yavaar.nosi.crm.entity.PaymentType;
import com.yavaar.nosi.crm.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceTest {

    @MockBean
    OrderDao orderDao;

    @Autowired
    OrderService orderService;

    private Order order;

    @BeforeEach()
    void setUp() {

        order = new Order(LocalDate.of(2023, 03, 05), new BigDecimal("100.00"),
                new BigDecimal("20.00"), new BigDecimal("140"), PaymentType.MASTERCARD);

    }

    @Test
    void saveOrderTest() {

        when(orderDao.save(order)).thenReturn(order);

        assertEquals(order, orderService.saveOrder(order));

        verify(orderDao, times(1)).save(order);

    }

    @Test
    void saveAllOrdersTest() {

        when(orderDao.saveAll(List.of(order))).thenReturn(List.of(order));

        orderService.saveAllOrders(List.of(order));

        verify(orderDao, times(1)).saveAll(List.of(order));

    }

    @Test
    void updateOrderTest() {

        when(orderDao.save(order)).thenReturn(order);

        orderService.updateOrder(order);

        verify(orderDao, times(1)).save(order);

    }

    @Test
    void deleteOrderByIdTest() {

        doNothing().when(orderDao).deleteById(1L);

        orderService.deleteOrderById(1L);

        verify(orderDao, times(1)).deleteById(1L);

    }

    @Test
    void checkIfStudentIsNullTest() {

        when(orderDao.findById(1L)).thenReturn(Optional.ofNullable(order));

        assertFalse(orderService.checkIfStudentIsNull(1L));

        verify(orderDao, times(1)).findById(1L);

    }

    @Test
    void findOrderByIdJoinFetchOrderDetailTest() {

        when(orderDao.findOrderByIdJoinFetchOrderDetail(1L)).thenReturn(order);

        assertEquals(order, orderService.findOrderByIdJoinFetchOrderDetail(1L));

        verify(orderDao, times(1)).findOrderByIdJoinFetchOrderDetail(1L);

    }

    @Test
    void findOrderByIdTest() {

        when(orderDao.findById(1L)).thenReturn(Optional.ofNullable(order));

        assertEquals(Optional.of(order), orderService.findOrderById(1L));

        verify(orderDao, times(1)).findById(1L);

    }

    @Test
    void findAllOrdersTest() {

        when(orderDao.findAll()).thenReturn(List.of(order));

        assertEquals(List.of(order), orderService.findAllOrders());

        verify(orderDao, times(1)).findAll();

    }

}
