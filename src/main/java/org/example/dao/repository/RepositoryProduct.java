package org.example.dao.repository;

import org.example.dao.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface RepositoryProduct {
     List<Product> getListProduct();
     Optional<Product> findProductById(int id);
    List<Product> getListStockOnProduct();
}
