package com.yavaar.nosi.crm.dao;

import com.yavaar.nosi.crm.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderServiceDetailDao extends JpaRepository<OrderDetail, Long> {
}
