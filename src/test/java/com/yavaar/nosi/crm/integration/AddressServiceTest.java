package com.yavaar.nosi.crm.integration;

import com.yavaar.nosi.crm.entity.Address;
import com.yavaar.nosi.crm.entity.Customer;
import com.yavaar.nosi.crm.service.AddressService;
import com.yavaar.nosi.crm.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource("/application-test.properties")
public class AddressServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    private JdbcTemplate jdbc;

    @Value("${sql.script.delete.address}")
    private String sqlDeleteAddress;
    private Address address;

    @Autowired
    private AddressService addressService;

    @BeforeEach
    public void setUp() {

        jdbc.execute(sqlDeleteAddress);

        address = new Address(58, "Windsor Road","Etobicoke" , "ON", "M9R-3G5");

    }

    @Test
    public void canAddAddress() {

        Address savedAddress = addressService.saveAddress(address);

        Address foundAddress = addressService.findAddressById(savedAddress.getId()).get();

        assertEquals(savedAddress.getId(), foundAddress.getId());

    }



}
