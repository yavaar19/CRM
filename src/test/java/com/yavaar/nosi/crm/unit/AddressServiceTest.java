package com.yavaar.nosi.crm.unit;

import com.yavaar.nosi.crm.dao.AddressDao;
import com.yavaar.nosi.crm.entity.Address;
import com.yavaar.nosi.crm.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@SpringBootTest
class AddressServiceTest {

    @MockBean
    AddressDao addressDao;

    @Autowired
    AddressService addressService;

    private Address address;

    @BeforeEach()
    void setUp() {

        address = new Address(23, "Johnson Lane", "Bradford", "ON", "L4F-3C4");

    }

    @Test
    void saveAddressTest() {

        when(addressDao.save(address)).thenReturn(address);

        assertEquals(address, addressService.saveAddress(address));

        verify(addressDao, times(1)).save(address);

    }

    @Test
    void updateAddressTest() {

        when(addressDao.save(address)).thenReturn(address);

        addressService.updateAddress(address);

        verify(addressDao, times(1)).save(address);

    }

    @Test
    void findAddressByIdTest() {

        when(addressDao.findById(1L)).thenReturn(Optional.ofNullable(address));

        assertEquals(Optional.of(address), addressService.findAddressById(1L));

        verify(addressDao, times(1)).findById(1L);

    }

    @Test
    void deleteAddressByIdTest() {

        doNothing().when(addressDao).deleteById(1L);

        addressService.deleteAddressById(1L);

        verify(addressDao, times(1)).deleteById(1L);

    }

    @Test
    void findAllAddressesTest() {

        when(addressDao.findAll()).thenReturn(List.of(address));

        assertEquals(List.of(address), addressService.findAllAddresses());

        verify(addressDao, times(1)).findAll();

    }

    @Test
    void findAddressByIdJoinFetchCustomerTest() {

        when(addressDao.findAddressByIdJoinFetchCustomer(1L)).thenReturn(address);

        assertEquals(address, addressService.findAddressByIdJoinFetchCustomer(1L));

        verify(addressDao, times(1)).findAddressByIdJoinFetchCustomer(1L);

    }

    @Test
    void checkIfAddressIsNullTest() {

        when(addressDao.findById(1L)).thenReturn(Optional.ofNullable(address));

        assertFalse(addressService.checkIfAddressIsNull(1L));

        verify(addressDao, times(1)).findById(1L);

    }

}
