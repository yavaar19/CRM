package com.yavaar.nosi.crm.service;

import com.yavaar.nosi.crm.dao.AddressDao;
import com.yavaar.nosi.crm.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressDao addressDao;

    @Override
    public Address saveAddress(Address address) {

        Address savedAddress = addressDao.save(address);

        return savedAddress;

    }

    @Override
    public Optional<Address> findAddressById(Long id) {

        Optional<Address> address = addressDao.findById(id);

        return address;

    }


}
