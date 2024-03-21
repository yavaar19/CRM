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
    public Customer saveCustomer(Customer customer) {

        Customer savedCustomer = customerDao.save(customer);

        return savedCustomer;

    }

    @Override
    public Optional <Customer> findCustomerById(long id) {

        Optional<Customer> foundCustomer = customerDao.findById(id);

        return foundCustomer;

    }

    @Override
    public void updateCustomer(Customer customer) {

        customerDao.save(customer);

    }

    @Override
    public void deleteCustomerById(long id) {

        customerDao.deleteById(id);

    }

    @Override
    public List<Customer> findAllCustomers() {

        List<Customer> customerList = customerDao.findAll();

        return customerList;

    }

    @Override
    public Customer findCustomerByIdJoinFetch(long id) {

        Customer customer = customerDao.findCustomerAndAddressesByCustomerId(id);

        return customer;

    }

}
