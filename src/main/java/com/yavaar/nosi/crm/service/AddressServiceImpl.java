package com.yavaar.nosi.crm.service;

import com.yavaar.nosi.crm.dao.AddressDao;
import com.yavaar.nosi.crm.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressDao addressDao;

    @Override
    public Address saveAddress(Address address) {

        return addressDao.save(address);

    }

    @Override
    public void updateAddress(Address address) {

        addressDao.save(address);

    }

    @Override
    public Optional<Address> findAddressById(long id) {

        return addressDao.findById(id);

    }

    public void deleteAddressById(long id) {

        addressDao.deleteById(id);

    }

    @Override
    public List<Address> findAllAddresses() {

        return addressDao.findAll();

    }

    @Override
    public Address findAddressByIdJoinFetchCustomer(long id) {

        return addressDao.findAddressByIdJoinFetchCustomer(id);

    }

    @Override
    public boolean checkIfAddressIsNull(long id) {

        Optional<Address> address = findAddressById(id);

        return address.isEmpty();


    }


}
