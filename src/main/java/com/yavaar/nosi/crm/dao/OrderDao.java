package com.yavaar.nosi.crm.dao;

import com.yavaar.nosi.crm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order, Long> {



}
