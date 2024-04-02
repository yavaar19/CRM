package com.yavaar.nosi.crm.dao;

import com.yavaar.nosi.crm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerDao extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT c FROM Customer c JOIN FETCH c.addresses WHERE c.id = :id")
    Optional<Customer> findCustomerByIdJoinFetchAddress(long id);
    @Query(value = "SELECT c FROM Customer c JOIN FETCH c.orders WHERE c.id = :id")
    Optional<Customer> findCustomerByIdJoinFetchOrder(long id);
    Optional<Customer> findCustomerByEmail(String email);
    @Query(value = "SELECT c FROM Customer c JOIN FETCH c.addresses")
    List<Customer> canFindAllCustomersJoinFetchAddress();

}
