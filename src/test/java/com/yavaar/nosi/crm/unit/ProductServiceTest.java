package com.yavaar.nosi.crm.unit;

import com.yavaar.nosi.crm.dao.ProductDao;
import com.yavaar.nosi.crm.entity.Product;
import com.yavaar.nosi.crm.entity.Status;
import com.yavaar.nosi.crm.exception.ProductNotFoundException;
import com.yavaar.nosi.crm.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    ProductDao productDao;

    @Autowired
    ProductService productService;

    Product product;

    @BeforeEach()
    void setUp() {

        product = new Product("SKU-3009", "Charger", "For Laptop", new BigDecimal("120.00"), Status.ACTIVE);

    }

    @Test
    void findProductByIdTest() {

        when(productDao.findById(1L)).thenReturn(Optional.ofNullable(product));

        assertEquals(Optional.of(product), productService.findProductById(1L));

        verify(productDao, times(1)).findById(1L);

    }

    @Test
    void saveProductTest() {

        when(productDao.save(product)).thenReturn(product);

        assertEquals(product, productService.saveProduct(product));

        verify(productDao, times(1)).save(product);

    }

    @Test
    void findAllProductsTest() {

        when(productDao.findAll()).thenReturn(List.of(product));

        assertEquals(List.of(product), productService.findAllProducts());

        verify(productDao, times(1)).findAll();

    }

    @Test
    void updateProduct() {

        when(productDao.save(product)).thenReturn(product);

        productService.updateProduct(product);

        verify(productDao, times(1)).save(product);

    }

    @Test
    void deleteProductByIdTest() {

        doNothing().when(productDao).deleteById(1L);

        productService.deleteProductById(1L);

        verify(productDao, times(1)).deleteById(1L);

    }

    @Test
    void checkIfProductIsNullTest() {

        when(productDao.findById(1L)).thenReturn(Optional.ofNullable(product));

        assertFalse(productService.checkIfProductIsNull(1L));

        verify(productDao, times(1)).findById(1L);

    }

    @Test
    void productNotFoundException() {

        Exception exception = assertThrows(ProductNotFoundException.class, () -> {

            productService.findProductById(100);

        });

        assertEquals("Product does not exist!", exception.getMessage());

    }

}
