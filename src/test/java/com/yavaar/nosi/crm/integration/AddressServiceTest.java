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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void canSaveAddress() {

        Address savedAddress = addressService.saveAddress(address);

        Address foundAddress = addressService.findAddressById(savedAddress.getId()).get();

        assertEquals(savedAddress, foundAddress);

    }

    @Test
    public void canFindAddress() {

        Address savedAddress = addressService.saveAddress(address);

        Address foundAddress = addressService.findAddressById(savedAddress.getId()).get();

        assertEquals(savedAddress, foundAddress);

    }

    @Test
    public void canFindAllAddresses() {

        Address address1 = new Address(380, "Dixon Road","Etobicoke" , "ON", "M9R-1T3");
        Address address2 = new Address(1735, "Kipling Ave","Etobicoke" , "ON", "M9R-4H5");
        addressService.saveAddress(address);
        addressService.saveAddress(address2);
        addressService.saveAddress(address1);

        List<Address> foundAddresses = addressService.findAllAddresses();

        assertEquals(3, foundAddresses.size());

    }

    @Test
    public void canUpdateAddress() {

        Address savedAddress = addressService.saveAddress(address);

        Address foundAddress = addressService.findAddressById(savedAddress.getId()).get();

        foundAddress.setStreetNumber(100);

        addressService.saveAddress(foundAddress);

        Address updatedAddress = addressService.findAddressById(savedAddress.getId()).get();

        assertEquals(foundAddress.getStreetNumber(), updatedAddress.getStreetNumber());

    }

    @Test
    public void canDeleteAddress() {

        Address savedAddress = addressService.saveAddress(address);

        addressService.deleteAddressById(savedAddress.getId());

        Optional<Address> foundAddress = addressService.findAddressById(savedAddress.getId());

        assertTrue(foundAddress.isEmpty());


    }



}
