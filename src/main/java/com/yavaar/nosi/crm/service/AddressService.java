package com.yavaar.nosi.crm.service;

import com.yavaar.nosi.crm.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    Address saveAddress(Address address);
    Optional<Address> findAddressById(Long id);
    void deleteAddressById(Long id);
    List<Address> findAllAddresses();

}
