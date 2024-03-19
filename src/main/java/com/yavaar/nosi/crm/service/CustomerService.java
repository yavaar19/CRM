package com.yavaar.nosi.crm.service;

import com.yavaar.nosi.crm.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer save(Customer customer);
    Optional<Customer> findById(long id);
    void updateCustomer(Customer customer);
    void deleteCustomer(Customer customer);
    List<Customer> findAll();

}
