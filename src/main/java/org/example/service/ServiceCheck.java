package org.example.service;

import org.example.dao.model.Check;
import org.example.dao.model.CheckLine;
import org.example.dao.model.DiscountCard;

import java.util.List;

public interface ServiceCheck {

    public CheckLine creatCheckLine(int count, int idProduct);


    public Check creatCheck(List<CheckLine> checkLineList, DiscountCard discountCard);

    public Check creatCheck(List<CheckLine> checkLineList);

    public double getCommonPriceCheck(Check check);

    public double getCommonPriceDiscount(Check check);

    public double getTotalPrice(Check check);

    public double getCommonPriceDiscountStock(Check check);

    double getTotalPriceWithStock(Check check);
}
