package com.yavaar.nosi.crm.service;

import com.yavaar.nosi.crm.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    Address saveAddress(Address address);
    void updateAddress(Address address);
    Optional<Address> findAddressById(long id);
    void deleteAddressById(long id);
    List<Address> findAllAddresses();
    Address findAddressByIdJoinFetchCustomer(long id);
    boolean checkIfAddressIsNull(long id);

}
