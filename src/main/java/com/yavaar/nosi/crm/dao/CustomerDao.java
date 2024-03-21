package com.yavaar.nosi.crm.dao;

import com.yavaar.nosi.crm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerDao extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT c FROM Customer c JOIN FETCH c.addresses WHERE c.id = :id")
    Customer findCustomerAndAddressesByCustomerId(long id);

}
