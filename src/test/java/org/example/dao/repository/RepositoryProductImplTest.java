package org.example.dao.repository;

import org.example.dao.model.Product;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class RepositoryProductImplTest {

    @Test
    public void findProductById() {
        Product product = new Product(1, "Молоко (1 литр) ", 2, true);
        Optional<Product> productOptional = new RepositoryProductImpl().findProductById(1);
        Product productTest = productOptional.get();
        assertEquals(productTest, product);
    }
}