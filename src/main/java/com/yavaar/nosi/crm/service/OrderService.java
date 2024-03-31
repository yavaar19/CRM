package com.yavaar.nosi.crm.service;

import com.yavaar.nosi.crm.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order saveOrder(Order order);
    Optional<Order> findOrderById(long id);
    List<Order> findAllOrders();
    void saveAllOrders(List<Order> orders);
    void updateOrder(Order order);
    void deleteOrderById(long id);
    boolean checkIfOrderIsNull(long id);
    Optional<Order> findOrderByIdJoinFetchOrderDetail(long id);

}
