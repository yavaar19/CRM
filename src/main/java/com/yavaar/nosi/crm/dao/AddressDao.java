package com.yavaar.nosi.crm.dao;

import com.yavaar.nosi.crm.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AddressDao extends JpaRepository<Address, Long> {

    @Query(value = "SELECT a FROM Address a JOIN FETCH a.customers WHERE a.id = :id")
    Address findAddressByIdJoinFetchCustomer(long id);


}
