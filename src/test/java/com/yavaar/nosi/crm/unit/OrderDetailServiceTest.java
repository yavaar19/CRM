package com.yavaar.nosi.crm.unit;


import com.yavaar.nosi.crm.dao.OrderServiceDetailDao;
import com.yavaar.nosi.crm.entity.OrderDetail;
import com.yavaar.nosi.crm.entity.Product;
import com.yavaar.nosi.crm.service.OrderDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderDetailServiceTest {

    @MockBean
    OrderServiceDetailDao orderServiceDetailDao;

    @Autowired
    OrderDetailService orderDetailService;

    private OrderDetail orderDetail;

    @BeforeEach()
    void setUp() {

        orderDetail = new OrderDetail(new Product(), 2, new BigDecimal("20.00"));

    }

    @Test
    void saveOrderDetailTest() {

        when(orderServiceDetailDao.save(orderDetail)).thenReturn(orderDetail);

        assertEquals(orderDetail, orderDetailService.saveOrderDetail(orderDetail));

        verify(orderServiceDetailDao, times(1)).save(orderDetail);

    }

    @Test
    void findOrderDetailByIdTest() {

        when(orderServiceDetailDao.findById(1L)).thenReturn(Optional.ofNullable(orderDetail));

        assertEquals(Optional.of(orderDetail), orderDetailService.findOrderDetailById(1L));

        verify(orderServiceDetailDao, times(1)).findById(1L);

    }

    @Test
    void saveAllOrderDetailsTest() {

        when(orderServiceDetailDao.saveAll(List.of(orderDetail))).thenReturn(List.of(orderDetail));

        orderDetailService.saveAllOrderDetails(List.of(orderDetail));

        verify(orderServiceDetailDao, times(1)).saveAll(List.of(orderDetail));

    }

    @Test
    void findAllOrderDetailsTest() {

        when(orderServiceDetailDao.findAll()).thenReturn(List.of(orderDetail));

        assertEquals(List.of(orderDetail), orderDetailService.findAllOrderDetails());

        verify(orderServiceDetailDao, times(1)).findAll();

    }

    @Test
    void updateOrderDetailTest() {

        when(orderServiceDetailDao.save(orderDetail)).thenReturn(orderDetail);

        orderDetailService.updateOrderDetail(orderDetail);

        verify(orderServiceDetailDao, times(1)).save(orderDetail);

    }

    @Test
    void deleteOrderDetailByIdTest() {

        doNothing().when(orderServiceDetailDao).deleteById(1L);

        orderDetailService.deleteOrderDetailById(1L);

        verify(orderServiceDetailDao, times(1)).deleteById(1L);

    }

    @Test
    void checkIfOrderDetailIsNullTest() {

        when(orderServiceDetailDao.findById(1L)).thenReturn(Optional.ofNullable(orderDetail));

        assertFalse(orderDetailService.checkIfOrderDetailIsNull(1L));

        verify(orderServiceDetailDao, times(1)).findById(1L);

    }


}
