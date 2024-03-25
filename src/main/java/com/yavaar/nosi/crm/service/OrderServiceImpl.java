package com.yavaar.nosi.crm.service;

import com.yavaar.nosi.crm.dao.OrderDao;
import com.yavaar.nosi.crm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderDao orderDao;

    @Override
    @Transactional
    public Order saveOrder(Order order) {

        return orderDao.save(order);

    }

    @Override
    public void saveAllOrders(List<Order> orders) {

        orderDao.saveAll(orders);

    }

    @Override
    public void updateOrder(Order order) {

        orderDao.save(order);

    }

    @Override
    public void deleteOrderById(long id) {

        orderDao.deleteById(id);

    }

    @Override
    public boolean checkIfStudentIsNull(long id) {

        Optional<Order> order = findOrderById(id);

        return order.isEmpty();

    }

    @Override
    public Optional<Order> findOrderById(long id) {

        return orderDao.findById(id);

    }

    @Override
    public List<Order> findAllOrders() {

        return orderDao.findAll();

    }

}
