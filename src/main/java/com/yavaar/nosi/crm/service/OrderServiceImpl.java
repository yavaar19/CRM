package com.yavaar.nosi.crm.service;

import com.yavaar.nosi.crm.dao.OrderDao;
import com.yavaar.nosi.crm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderDao orderDao;

    @Override
    public Order saveOrder(Order order) {

        return orderDao.save(order);

    }

    @Override
    public Optional<Order> findOrderById(Long id) {

        return orderDao.findById(id);

    }

    @Override
    public List<Order> findAllOrders() {

        return orderDao.findAll();

    }


}
