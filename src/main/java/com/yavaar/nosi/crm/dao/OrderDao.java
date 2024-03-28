package com.yavaar.nosi.crm.dao;

import com.yavaar.nosi.crm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderDao extends JpaRepository<Order, Long> {

    @Query(value = "SELECT o FROM Order o JOIN FETCH o.orderDetails where o.id =:id")
    Order findOrderByIdJoinFetchOrderDetail(long id);

}
