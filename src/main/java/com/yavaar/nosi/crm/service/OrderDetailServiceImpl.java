package com.yavaar.nosi.crm.service;

import com.yavaar.nosi.crm.dao.OrderServiceDetailDao;
import com.yavaar.nosi.crm.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderServiceDetailDao orderServiceDetailDao;

    @Override
    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {

        return orderServiceDetailDao.save(orderDetail);

    }

    @Override
    public Optional <OrderDetail> findOrderDetailById(long id) {

        return orderServiceDetailDao.findById(id);

    }

    @Override
    public void saveAllOrderDetails(List<OrderDetail> orderDetails) {

        orderServiceDetailDao.saveAll(orderDetails);

    }

    @Override
    public List<OrderDetail> findAllOrderDetails() {

        return orderServiceDetailDao.findAll();

    }

    @Override
    public void updateOrderDetail(OrderDetail orderDetail) {

        orderServiceDetailDao.save(orderDetail);

    }

    @Override
    public void deleteOrderDetailById(long id) {

        orderServiceDetailDao.deleteById(id);

    }

    @Override
    public boolean checkIfOrderDetailIsNull(long id) {

        Optional<OrderDetail> orderDetail = findOrderDetailById(id);

        return orderDetail.isEmpty();

    }

}
