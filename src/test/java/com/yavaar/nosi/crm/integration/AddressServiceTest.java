package com.yavaar.nosi.crm.integration;

import com.yavaar.nosi.crm.entity.Address;
import com.yavaar.nosi.crm.entity.Customer;
import com.yavaar.nosi.crm.service.AddressService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class AddressServiceTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Value("${sql.script.delete.customer}")
    private String SQLDELETECUSTOMER;

    @Value("${sql.script.delete.address}")
    private String SQLDELETEADDRESS;

    @Value("${sql.script.delete.customer_address}")
    private String SQLDELETECUSTOMERADDRESS;

    @Autowired
    private AddressService addressService;

    @BeforeEach
    void setUp() {

        jdbc.execute("INSERT INTO ADDRESS(ID, STREET_NUMBER, STREET_NAME, CITY, PROVINCE, POSTAL_CODE)"
                + "VALUES(1, 58, 'Windsor Road', 'Etobicoke', 'ON', 'M9R-3G5')");

    }

    @AfterEach
    void cleanUpDatabase() {

        jdbc.execute(SQLDELETECUSTOMERADDRESS);
        jdbc.execute(SQLDELETECUSTOMER);
        jdbc.execute(SQLDELETEADDRESS);

    }

    @Test
    void canSaveAddress() {

        Address address = new Address(58, "Windsor Road","Etobicoke" , "ON", "M9R-3G5");

        Address savedAddress = addressService.saveAddress(address);
        Address foundAddress = addressService.findAddressById(savedAddress.getId()).get();

        assertEquals(savedAddress, foundAddress);

    }

    @Test
    void canFindAddress() {

        Optional<Address> foundAddress = addressService.findAddressById(1);

        assertTrue(foundAddress.isPresent());

    }

    @Test
    void canFindAllAddresses() {

        jdbc.execute("INSERT INTO ADDRESS(ID, STREET_NUMBER, STREET_NAME, CITY, PROVINCE, POSTAL_CODE)"
                + "VALUES(2, 380, 'Dixon Road', 'Etobicoke', 'ON', 'M9R-1T3')");
        jdbc.execute("INSERT INTO ADDRESS(ID, STREET_NUMBER, STREET_NAME, CITY, PROVINCE, POSTAL_CODE)"
                + "VALUES(3, 1735, 'Kipling Ave', 'Etobicoke', 'ON', 'M9R-4H6')");
        jdbc.execute("INSERT INTO ADDRESS(ID, STREET_NUMBER, STREET_NAME, CITY, PROVINCE, POSTAL_CODE)"
                + "VALUES(4, 95, 'Islington Ave', 'Etobicoke', 'ON', 'M9R-8J7')");

        List<Address> foundAddresses = addressService.findAllAddresses();

        assertEquals(4, foundAddresses.size());

    }

    @Test
    void canUpdateAddress() {

        Optional<Address> foundAddress = addressService.findAddressById(1);

        assertEquals(58, foundAddress.get().getStreetNumber());

        foundAddress.get().setStreetNumber(100);

        addressService.updateAddress(foundAddress.get());

        Address updatedAddress = addressService.findAddressById(1).get();

        assertEquals(100, updatedAddress.getStreetNumber());

    }

    @Test
    void canDeleteAddress() {

        assertTrue(addressService.findAddressById(1).isPresent());

        addressService.deleteAddressById(1);

        Optional<Address> foundAddress = addressService.findAddressById(1);

        assertTrue(foundAddress.isEmpty());

    }

    @Test
    void canFindAddressByIdJoinFetchCustomer() {

        Address address = new Address(58, "Windsor Road","Etobicoke" , "ON", "M9R-3G5");
        Customer customer = new Customer("Yavaar", "Nosimohomed", "yavaar@gmail.com", LocalDate.of(1989, 10, 01));
        address.addCustomer(customer);
        Address savedAddress = addressService.saveAddress(address);

        Address foundAddress = addressService.findAddressByIdJoinFetchCustomer(savedAddress.getId());

        assertFalse(foundAddress.getCustomers().isEmpty());


    }

    @Test
    void isAddressNullCheck() {

        assertFalse(addressService.checkIfAddressIsNull(1));
        assertTrue(addressService.checkIfAddressIsNull(0));

    }

}
