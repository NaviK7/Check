package org.example.dao.repository;

import file.ReadFileProduct;
import org.example.dao.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryProductImpl implements RepositoryProduct {
    private static final ReadFileProduct READ_FILE = new ReadFileProduct();

    //константа в виде массива, хращего id прдуктов, на которые распротраняется акция(скидка в 10%)
    private final int[] stock = new int[]{1, 3, 8};

    public List<Product> getListProduct() {
        List<Product> productList = new ArrayList<>();
        READ_FILE.readLines();
        String text = READ_FILE.readLines();
        text = text.replaceAll("\\s+", " ").replace(",", ".");
        String[] products = text.split("BYN");
        for (int i = 0; i < products.length; i++) {
            String[] product = products[i].split(":");
            for (int j = 0; j < 1; j++) {
                productList.add(new Product(i, product[j], Double.parseDouble(product[j + 1])));
            }
        }
        return productList;
    }

    // возвращает список товаров с учетом аукционных товаров
    public List<Product> getListStockOnProduct() {
        List<Product> productList = getListProduct();
        for (Product product : productList) {
            for (int j : stock) {
                if (product.getId() == j) {
                    product.setDiscountStatus(true);
                }
            }
        }
        return productList;
    }

    public Optional<Product> findProductById(int id) {
        try {
            for (Product product : getListStockOnProduct()) {
                if (product.getId() == id) {
                    return Optional.of(product);
                }
            }
        } catch (RuntimeException exception) {
            System.out.println("Ошибочка");
        }
        return Optional.empty();
    }
}