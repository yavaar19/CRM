package com.yavaar.nosi.crm.service;

import com.yavaar.nosi.crm.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> findProductById(long id);
    Product saveProduct(Product product);
    List<Product> findAllProducts();
    void updateProduct(Product product);
    void deleteProductById(long id);
    boolean checkIfProductIsNull(long id);

}
