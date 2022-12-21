package org.example.service;

import org.example.dao.model.Check;
import org.example.dao.model.CheckLine;
import org.example.dao.model.DiscountCard;
import org.example.dao.model.Product;
import org.example.dao.repository.RepositoryProduct;
import org.example.dao.repository.RepositoryProductImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.ROUND_CEILING;
import static java.math.BigDecimal.ROUND_DOWN;

public class ServiceCheckImpl implements ServiceCheck {
    private final RepositoryProduct repositoryProduct = new RepositoryProductImpl();


    @Override
    public CheckLine creatCheckLine(int count, int idProduct) {
        Optional<Product> productOptional = repositoryProduct.findProductById(idProduct);
        Product product = productOptional.get();
        CheckLine checkLine = new CheckLine();
        checkLine.setProduct(product);
        checkLine.setCountProduct(count);
        checkLine.setCommonPrice(product.getPrice() * count);
        return checkLine;
    }

    public Check creatCheck(List<CheckLine> checkLineList, DiscountCard discountCard) {
        return new Check(checkLineList, discountCard);
    }

    public Check creatCheck(List<CheckLine> checkLineList) {
        return new Check(checkLineList);
    }

    //возвращает общую стоимость всех покупок
    public double getCommonPriceCheck(Check check) {
        double commonPriceCheck = 0;
        for (CheckLine checkLine : check.getCheckLineList()) {
            commonPriceCheck = commonPriceCheck + checkLine.getCommonPrice();
        }
        BigDecimal result = new BigDecimal(commonPriceCheck);
        result = result.setScale(2, ROUND_DOWN);
        commonPriceCheck = result.doubleValue();
        return commonPriceCheck;
    }

    //возвращает скидку от общей стоимости товара
    public double getCommonPriceDiscount(Check check) {
        double commonDiscount = getCommonPriceCheck(check);
        commonDiscount = commonDiscount * check.getDiscountCard().getDiscountPercentage() / 100;
        BigDecimal result = new BigDecimal(commonDiscount);
        result = result.setScale(2, ROUND_CEILING);
        commonDiscount = result.doubleValue();
        return commonDiscount;
    }

    //возвращает разницу от общей общей стоимости и общей скидки,с учетом или без учета скидочной карты
    public double getTotalPrice(Check check) {
        BigDecimal result;
        if (check.getDiscountCard() != null) {
            result = new BigDecimal(getCommonPriceCheck(check) - getCommonPriceDiscount(check));
            result = result.setScale(2, ROUND_DOWN);
        } else {
            result = new BigDecimal(getCommonPriceCheck(check));
            result = result.setScale(2, ROUND_DOWN);
        }
        return result.doubleValue();
    }


    //возвращает скику по акции, если есть аукционный товар и его количество больше 5
    public double getCommonPriceDiscountStock(Check check) {
        double commonPriceDiscountStock = 0;
        int count = 0;
        for (CheckLine checkLine : check.getCheckLineList()) {
            if (checkLine.getProduct().isDiscountStatus() && checkLine.getCountProduct() > 5) {
                commonPriceDiscountStock = commonPriceDiscountStock + checkLine.getCommonPrice();
            }
        }
        commonPriceDiscountStock = commonPriceDiscountStock * 0.1;
        BigDecimal result = new BigDecimal(commonPriceDiscountStock);
        result = result.setScale(2, ROUND_DOWN);
        commonPriceDiscountStock = result.doubleValue();
        return commonPriceDiscountStock;
    }

    //возвращает общую стоимость с учетом всех скидок, если они есть
    public double getTotalPriceWithStock(Check check) {
        BigDecimal result = new BigDecimal(getTotalPrice(check) - getCommonPriceDiscountStock(check));
        result = result.setScale(2, ROUND_DOWN);
        return result.doubleValue();
    }
}
