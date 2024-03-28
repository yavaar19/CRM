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

        return customerDao.save(customer);

    }

    @Override
    public Optional <Customer> findCustomerById(long id) {

        return customerDao.findById(id);

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

        return customerDao.findAll();

    }

    @Override
    public Customer findCustomerByIdJoinFetchAddress(long id) {

        return customerDao.findCustomerByIdJoinFetchAddress(id);

    }

    @Override
    public boolean checkIfCustomerIsNull(long id) {

        Optional<Customer> customer = findCustomerById(id);

        return customer.isEmpty();

    }

    @Override
    public Customer findCustomerByIdJoinFetchOrder(long id) {

        return customerDao.findCustomerByIdJoinFetchOrder(id);


    }

}
