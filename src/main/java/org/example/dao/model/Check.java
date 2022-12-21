package org.example.dao.model;

import java.util.List;
import java.util.Objects;

public class Check {
    private final List<CheckLine> checkLineList;
    private DiscountCard discountCard;

    public Check(List<CheckLine> checkLineList, DiscountCard discountCard) {
        this.checkLineList = checkLineList;
        this.discountCard = discountCard;
    }

    public Check(List<CheckLine> checkLineList) {
        this.checkLineList = checkLineList;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public List<CheckLine> getCheckLineList() {
        return checkLineList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Check check = (Check) o;
        return Objects.equals(checkLineList, check.checkLineList) && Objects.equals(discountCard, check.discountCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkLineList, discountCard);
    }

    @Override
    public String toString() {
        return
                checkLineList + "";
    }
}
