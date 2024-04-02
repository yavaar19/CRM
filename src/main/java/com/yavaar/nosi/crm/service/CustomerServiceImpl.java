package com.yavaar.nosi.crm.service;

import com.yavaar.nosi.crm.dao.CustomerDao;
import com.yavaar.nosi.crm.entity.Customer;
import com.yavaar.nosi.crm.exception.CustomerNotFoundException;
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
    public Optional<Customer> findCustomerById(long id) {

        Optional<Customer> customer = customerDao.findById(id);

        if (customer.isEmpty()) {

            throw new CustomerNotFoundException("Customer does not exist!");

        }

        return customer;

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
    public Optional<Customer> findCustomerByIdJoinFetchAddress(long id) {

        Optional<Customer> customer = customerDao.findCustomerByIdJoinFetchAddress(id);

        if (customer.isEmpty()) {

            customer = customerDao.findById(id);

        }

        return customer;

    }

    @Override
    public Optional<Customer> findCustomerByIdJoinFetchOrder(long id) {

        Optional<Customer> customer = customerDao.findCustomerByIdJoinFetchOrder(id);

        if (customer.isEmpty()) {

            customer = customerDao.findById(id);

        }

        return customer;

    }

    @Override
    public boolean checkIfCustomerIsNull(long id) {

        Optional<Customer> customer = customerDao.findById(id);

        return customer.isEmpty();

    }

    @Override
    public Optional<Customer> findCustomerByEmailAddress(String email) {

        Optional<Customer> customer = customerDao.findCustomerByEmail(email);

        if (customer.isEmpty()) {

            throw new CustomerNotFoundException("Customer does not exist!");

        }

        return customer;

    }

    @Override
    public List<Customer> findAllCustomersJoinFetchAddress() {

        return customerDao.canFindAllCustomersJoinFetchAddress();

    }

}
