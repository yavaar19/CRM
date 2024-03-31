package com.yavaar.nosi.crm.dao;

import com.yavaar.nosi.crm.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface AddressDao extends JpaRepository<Address, Long> {

    @Query(value = "SELECT a FROM Address a JOIN FETCH a.customers WHERE a.id = :id")
    Optional<Address> findAddressByIdJoinFetchCustomer(long id);


}
