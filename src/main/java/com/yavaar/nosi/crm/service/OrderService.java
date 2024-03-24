package com.yavaar.nosi.crm.service;

import com.yavaar.nosi.crm.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order saveOrder(Order order);
    Optional<Order> findOrderById(Long id);
    List<Order> findAllOrders();

}
