package org.example.dao.model;

import java.util.Objects;

public class Product {
    private int id;
    private String name;
    private double price;

    private boolean discountStatus;

    public Product(int id, String name, double price, boolean discountStatus) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discountStatus = discountStatus;
    }

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isDiscountStatus() {
        return discountStatus;
    }

    public void setDiscountStatus(boolean discountStatus) {
        this.discountStatus = discountStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Double.compare(product.price, price) == 0 && discountStatus == product.discountStatus && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, discountStatus);
    }

    @Override
    public String toString() {
        return " " + name + "  - " + price + " BYN" + " stock: " + discountStatus;
    }
}
