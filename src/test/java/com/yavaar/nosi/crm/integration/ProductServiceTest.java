package com.yavaar.nosi.crm.integration;

import com.yavaar.nosi.crm.entity.Product;
import com.yavaar.nosi.crm.entity.Status;
import com.yavaar.nosi.crm.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    private JdbcTemplate jdbc;

    @Value("${sql.script.delete.product}")
    private String SQLDELETEPRODUCT;

    Product product;

    @BeforeEach
    void setUp() {

        jdbc.execute("INSERT INTO PRODUCT(ID, SKU, NAME, DESCRIPTION, PRICE, STATUS)"
                + "VALUES(10, 'SKU-123', 'CHAIR', 'BLACKCHAIR', 324.78, 'ACTIVE')");

    }

    @Test
    void canSaveProduct() {

        product = new Product("SKU-786", "Bedframe", "Luxury bedframe", new BigDecimal("259.46"), Status.ACTIVE);

        Product savedProduct = productService.saveProduct(product);

        Product foundProduct = productService.findProductById(savedProduct.getId()).get();

        assertEquals(savedProduct, foundProduct);

    }

    @Test
    void canFindProduct() {

        Optional<Product> foundProduct = productService.findProductById(10);

        assertTrue(foundProduct.isPresent());

    }

    @Test
    void canFindAllProducts() {

        jdbc.execute("INSERT INTO PRODUCT(ID, SKU, NAME, DESCRIPTION, PRICE, STATUS)"
                + "VALUES(24, 'SKU-247', 'Table', 'Black', 100.78, 'ACTIVE')");
        jdbc.execute("INSERT INTO PRODUCT(ID, SKU, NAME, DESCRIPTION, PRICE, STATUS)"
                + "VALUES(34, 'SKU-045', 'Mouse', 'Wireless', 154.99, 'DISCONTINUED')");
        jdbc.execute("INSERT INTO PRODUCT(ID, SKU, NAME, DESCRIPTION, PRICE, STATUS)"
                + "VALUES(205, 'SKU-925', 'Keyboard', 'With Touchpad', 99.99, 'ACTIVE')");

        List<Product> foundProducts = productService.findAllProducts();

        assertEquals(4, foundProducts.size());

    }

    @Test
    void canUpdateProduct() {

        Product foundProduct = productService.findProductById(10).get();
        foundProduct.setName("IPhone");

        productService.updateProduct(foundProduct);

        Product updatedProduct = productService.findProductById(10).get();

        assertEquals(foundProduct, updatedProduct);

    }

    @Test
    void canDeleteProduct() {

        Optional<Product> foundProduct = productService.findProductById(10);

        assertTrue(foundProduct.isPresent());

        productService.deleteProductById(10);

        Optional<Product> deletedProduct = productService.findProductById(10);

        assertFalse(deletedProduct.isPresent());

    }

    @Test
    void isProductNullCheck() {

        assertFalse(productService.checkIfProductIsNull(10));
        assertTrue(productService.checkIfProductIsNull(0));

    }


    @AfterEach
    void cleanUpDatabase() {

        jdbc.execute(SQLDELETEPRODUCT);

    }

}
