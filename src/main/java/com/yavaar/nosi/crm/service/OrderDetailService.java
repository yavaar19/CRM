package com.yavaar.nosi.crm.service;

import com.yavaar.nosi.crm.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {

    Optional <OrderDetail> findOrderDetailById(long id);
    OrderDetail saveOrderDetail(OrderDetail orderDetail);
    void saveAllOrderDetails(List<OrderDetail> orderDetails);
    List<OrderDetail> findAllOrderDetails();
    void updateOrderDetail(OrderDetail orderDetail);
    void deleteOrderDetailById(long id);
    boolean checkIfOrderDetailIsNull(long id);

}
