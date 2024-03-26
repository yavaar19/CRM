package com.yavaar.nosi.crm.dao;

import com.yavaar.nosi.crm.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Long> {


}
