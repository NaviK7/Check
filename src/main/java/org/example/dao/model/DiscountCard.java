package org.example.dao.model;

import java.util.Objects;

public class DiscountCard {
    private int number;
    private int discountPercentage;

    public DiscountCard(int number, int discountPercentage) {
        if (String.valueOf(number).length() == 4) {
            this.number = number;
            this.discountPercentage = discountPercentage;
        } else throw new RuntimeException("Номер карты должен состоять из четырех цифр");

    }

    public DiscountCard(int number) {
        this.number = number;
    }

    public DiscountCard() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountCard that = (DiscountCard) o;
        return number == that.number && discountPercentage == that.discountPercentage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, discountPercentage);
    }

    @Override
    public String toString() {
        return "discountCard{" +
                "number= " + number +
                ", discount % =" + discountPercentage +
                '}';
    }
}
