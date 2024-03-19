package com.yavaar.nosi.crm.dao;

import com.yavaar.nosi.crm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<Customer, Long> {


}
