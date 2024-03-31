package com.yavaar.nosi.crm.integration;

import com.yavaar.nosi.crm.entity.Address;
import com.yavaar.nosi.crm.entity.Customer;
import com.yavaar.nosi.crm.exception.AddressNotFoundException;
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
                + "VALUES(1, 100, 'Cedar Lane', 'Bradford', 'ON', 'L9H-D1T')");

    }

    @AfterEach
    void cleanUpDatabase() {

        jdbc.execute(SQLDELETECUSTOMERADDRESS);
        jdbc.execute(SQLDELETECUSTOMER);
        jdbc.execute(SQLDELETEADDRESS);

    }

    @Test
    void canSaveAddress() {

        Address address = new Address(100, "Cedar Lane","Bradford" , "ON", "L9H-D1T");

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
                + "VALUES(2, 48, 'Luda Road', 'Bradford', 'ON', 'M9R-1T3')");
        jdbc.execute("INSERT INTO ADDRESS(ID, STREET_NUMBER, STREET_NAME, CITY, PROVINCE, POSTAL_CODE)"
                + "VALUES(3, 1735, 'Kipling Ave', 'Bradford', 'ON', 'M9R-4H6')");
        jdbc.execute("INSERT INTO ADDRESS(ID, STREET_NUMBER, STREET_NAME, CITY, PROVINCE, POSTAL_CODE)"
                + "VALUES(4, 95, 'Islington Ave', 'Bradford', 'ON', 'M9R-8J7')");

        List<Address> foundAddresses = addressService.findAllAddresses();

        assertEquals(4, foundAddresses.size());

    }

    @Test
    void canUpdateAddress() {

        Optional<Address> foundAddress = addressService.findAddressById(1);

        assertEquals(100, foundAddress.get().getStreetNumber());

        foundAddress.get().setStreetNumber(100);

        addressService.updateAddress(foundAddress.get());

        Address updatedAddress = addressService.findAddressById(1).get();

        assertEquals(100, updatedAddress.getStreetNumber());

    }

    @Test
    void canDeleteAddress() {

        assertTrue(addressService.findAddressById(1).isPresent());

        addressService.deleteAddressById(1);

        assertTrue(addressService.checkIfAddressIsNull(1));

    }

    @Test
    void canFindAddressByIdJoinFetchCustomer() {

        Address address = new Address(100, "Cedar Lane","Bradford" , "ON", "L9H-D1T");
        Customer customer = new Customer("Varun", "Pandit", "pandit@gmail.com", LocalDate.of(1989, 10, 01));
        address.addCustomer(customer);
        Address savedAddress = addressService.saveAddress(address);

        Address foundAddress = addressService.findAddressByIdJoinFetchCustomer(savedAddress.getId()).get();

        assertFalse(foundAddress.getCustomers().isEmpty());


    }

    @Test
    void isAddressNullCheck() {

        assertFalse(addressService.checkIfAddressIsNull(1));

        assertTrue(addressService.checkIfAddressIsNull(0));

    }

    @Test
    void addressNotFoundException() {

        Exception exception = assertThrows(AddressNotFoundException.class, () -> {

            addressService.findAddressById(100);

        });

        assertEquals("Address does not exist!", exception.getMessage());

    }

}
