package com.yavaar.nosi.crm.service;

import com.yavaar.nosi.crm.dao.CustomerDao;
import com.yavaar.nosi.crm.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerDao customerDao;

    @Override
    public Customer save(Customer customer) {

        Customer savedCustomer = customerDao.save(customer);

        return savedCustomer;

    }

    @Override
    public Optional <Customer> findById(long id) {

        Optional<Customer> foundCustomer = customerDao.findById(id);

        return foundCustomer;

    }

    @Override
    public void updateCustomer(Customer customer) {

        customerDao.save(customer);

    }

    @Override
    public void deleteCustomer(Customer customer) {

        customerDao.delete(customer);

    }

    @Override
    public List<Customer> findAll() {

        List<Customer> customerList = customerDao.findAll();

        return customerList;

    }

    @Override
    public Customer findCustomerAndAddressesByCustomerId(long id) {

        Customer customer = customerDao.findCustomerAndAddressesByCustomerId(id);

        return customer;

    }

}
