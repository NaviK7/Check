package org.example.dao.model;

import java.util.Objects;

public class CheckLine {
    private int countProduct;

    public Product product;

    private double commonPrice;

    public CheckLine(int countProduct, Product product, double commonPrice) {
        this.countProduct = countProduct;
        this.product = product;
        this.commonPrice = commonPrice;
    }

    public CheckLine() {
    }

    public int getCountProduct() {
        return countProduct;
    }

    public void setCountProduct(int countProduct) {
        this.countProduct = countProduct;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getCommonPrice() {
        return commonPrice;
    }

    public void setCommonPrice(double commonPrice) {
        this.commonPrice = commonPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckLine checkLine = (CheckLine) o;
        return countProduct == checkLine.countProduct && Double.compare(checkLine.commonPrice, commonPrice) == 0 && Objects.equals(product, checkLine.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countProduct, product, commonPrice);
    }

    @Override
    public String toString() {
        return countProduct +
                "   " + product +
                "  common: " + commonPrice + " BYN" + "\n";
    }
}
