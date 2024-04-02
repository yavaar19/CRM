package com.yavaar.nosi.crm.service;

import com.yavaar.nosi.crm.dao.AddressDao;
import com.yavaar.nosi.crm.entity.Address;
import com.yavaar.nosi.crm.exception.AddressNotFoundException;
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

        Optional<Address> address = addressDao.findById(id);

        if (address.isEmpty()) {

            throw new AddressNotFoundException("Address does not exist!");

        }

        return address;

    }

    public void deleteAddressById(long id) {

        addressDao.deleteById(id);

    }

    @Override
    public List<Address> findAllAddresses() {

        return addressDao.findAll();

    }

    @Override
    public Optional<Address> findAddressByIdJoinFetchCustomer(long id) {

        Optional<Address> address = addressDao.findAddressByIdJoinFetchCustomer(id);

        if (address.isEmpty()) {

            address = addressDao.findById(id);

        }

        return address;

    }

    @Override
    public boolean checkIfAddressIsNull(long id) {

        Optional<Address> address = addressDao.findById(id);

        return address.isEmpty();

    }


}
