package com.yavaar.nosi.crm.unit;


import com.yavaar.nosi.crm.dao.CustomerDao;
import com.yavaar.nosi.crm.entity.Customer;
import com.yavaar.nosi.crm.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest()
class CustomerServiceTest {

    @MockBean
    CustomerDao customerDao;

    @Autowired
    CustomerService customerService;

    private Customer customer;

    @BeforeEach()
    void setUp() {

        customer = new Customer("Jack", "Sparrow", "jack@gmail.com", LocalDate.of(1987, 3, 12));

    }

    @Test
    void saveCustomerTest() {

        when(customerDao.save(customer)).thenReturn(customer);

        assertEquals(customer, customerService.saveCustomer(customer));

        verify(customerDao, times(1)).save(customer);

    }

    @Test
    void findCustomerByIdTest() {

        when(customerDao.findById(1L)).thenReturn(Optional.ofNullable(customer));

        assertEquals(Optional.of(customer), customerService.findCustomerById(1L));

        verify(customerDao, times(1)).findById(1L);

    }

    @Test
    void updateCustomerTest() {

        when(customerDao.save(customer)).thenReturn(customer);

        customerService.updateCustomer(customer);

        verify(customerDao, times(1)).save(customer);

    }

    @Test
    void deleteCustomerByIdTest() {

        doNothing().when(customerDao).deleteById(1L);

        customerService.deleteCustomerById(1L);

        verify(customerDao, times(1)).deleteById(1L);

    }

    @Test
    void findAllCustomersTest() {

        when(customerDao.findAll()).thenReturn(List.of(customer));

        assertEquals(List.of(customer), customerService.findAllCustomers());

        verify(customerDao, times(1)).findAll();

    }

    @Test
    void findCustomerByIdJoinFetchAddressTest() {

        when(customerDao.findCustomerByIdJoinFetchAddress(1L)).thenReturn(customer);

        assertEquals(customer, customerService.findCustomerByIdJoinFetchAddress(1L));

        verify(customerDao, times(1)).findCustomerByIdJoinFetchAddress(1L);

    }

    @Test
    void checkIfCustomerIsNullTest() {

        when(customerDao.findById(1L)).thenReturn(Optional.ofNullable(customer));

        assertFalse(customerService.checkIfCustomerIsNull(1L));

        verify(customerDao, times(1)).findById(1L);

    }

    @Test
    void findCustomerByIdJoinFetchOrderTest() {

        when(customerDao.findCustomerByIdJoinFetchOrder(1L)).thenReturn(customer);

        assertEquals(customer, customerService.findCustomerByIdJoinFetchOrder(1L));

        verify(customerDao, times(1)).findCustomerByIdJoinFetchOrder(1L);

    }

}
