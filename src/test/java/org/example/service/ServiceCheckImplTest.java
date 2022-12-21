package org.example.service;

import org.example.dao.model.Check;
import org.example.dao.model.CheckLine;
import org.example.dao.model.DiscountCard;
import org.example.dao.model.Product;
import org.example.dao.repository.RepositoryCardImpl;
import org.example.dao.repository.RepositoryProductImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceCheckImplTest {

    @Test
    public void creatCheckLine() {
        Optional<Product> productOptional = new RepositoryProductImpl().findProductById(0);
        Product product = productOptional.get();
        CheckLine checkLine = new ServiceCheckImpl().creatCheckLine(2, 0);
        Product productTest = new Product(0, "Капучино в кофейне ", 4.45);
        CheckLine checkLineTest = new CheckLine(2, productTest, 8.9);
        assertEquals(checkLineTest, checkLine);
    }


    @Test
    public void creatCheck() {
        List<CheckLine> checkLineList = new ArrayList<>();
        List<CheckLine> checkLineListTest = new ArrayList<>();
        DiscountCard discountCard = new DiscountCard(1234, 3);
        DiscountCard discountCardTest = new DiscountCard();
        Optional<DiscountCard> discountCardOptional = new RepositoryCardImpl().findOneDiscountCard(1234);
        if (discountCardOptional.isPresent()) {
            discountCardTest = discountCardOptional.get();
        }
        checkLineList.add(new CheckLine(2, new Product(0, "Капучино в кофейне ", 4.45, false), 8.9));
        checkLineList.add(new CheckLine(2, new Product(1, "Молоко (1 литр) ", 2, true), 4));

        Check check = new ServiceCheckImpl().creatCheck(checkLineList, discountCard);

        Optional<Product> productOptional = new RepositoryProductImpl().findProductById(0);
        Product product = productOptional.get();
        CheckLine checkLine = new CheckLine();
        checkLine.setProduct(product);
        checkLine.setCountProduct(2);
        checkLine.setCommonPrice(product.getPrice() * 2);

        Optional<Product> productOptional1 = new RepositoryProductImpl().findProductById(1);
        Product product1 = productOptional1.get();
        CheckLine checkLine1 = new CheckLine();
        checkLine1.setProduct(product1);
        checkLine1.setCountProduct(2);
        checkLine1.setCommonPrice(product1.getPrice() * 2);
        checkLineListTest.add(checkLine);
        checkLineListTest.add(checkLine1);
        Check checkTest = new ServiceCheckImpl().creatCheck(checkLineListTest, discountCardTest);
        assertEquals(checkTest, check);
    }

    @Test
    public void getCommonPriceCheck() {
        List<CheckLine> checkLineList = new ArrayList<>();
        checkLineList.add(new CheckLine(2, new Product(0, "Капучино в кофейне ", 4.45, false), 8.9));
        checkLineList.add(new CheckLine(2, new Product(1, "Молоко (1 литр) ", 2, true), 4));
        Check check = new Check(checkLineList);
        double commonPriceCheck = new ServiceCheckImpl().getCommonPriceCheck(check);
        assertEquals(commonPriceCheck, 12.9);
    }

    @Test
    public void getCommonPriceDiscount() {
        DiscountCard discountCard = new DiscountCard(1234, 3);
        List<CheckLine> checkLineList = new ArrayList<>();
        checkLineList.add(new CheckLine(2, new Product(0, "Капучино в кофейне ", 4.45, false), 8.9));
        checkLineList.add(new CheckLine(2, new Product(1, "Молоко (1 литр) ", 2, true), 4));
        Check check = new Check(checkLineList, discountCard);
        double commonPriceCheck = new ServiceCheckImpl().getCommonPriceDiscount(check);
        assertEquals(commonPriceCheck, 0.39);
    }

    @Test
    public void getTotalPrice() {
        Optional<DiscountCard> discountCardOptional = new RepositoryCardImpl().findOneDiscountCard(1234);
        DiscountCard discountCard = discountCardOptional.get();
        List<CheckLine> checkLineList = new ArrayList<>();
        checkLineList.add(new CheckLine(2, new Product(0, "Капучино в кофейне ", 4.45, false), 8.9));
        checkLineList.add(new CheckLine(2, new Product(1, "Молоко (1 литр) ", 2, true), 4));
        Check check = new Check(checkLineList, discountCard);
        double commonPriceCheck = new ServiceCheckImpl().getTotalPrice(check);
        assertEquals(commonPriceCheck, 12.5);
    }

    @Test
    public void getTotalPrice1() {
        List<CheckLine> checkLineList = new ArrayList<>();
        checkLineList.add(new CheckLine(2, new Product(0, "Капучино в кофейне ", 4.45, false), 8.9));
        checkLineList.add(new CheckLine(2, new Product(1, "Молоко (1 литр) ", 2, true), 4));
        Check check = new Check(checkLineList);
        double commonPriceCheck = new ServiceCheckImpl().getTotalPrice(check);
        assertEquals(commonPriceCheck, 12.9);
    }

    @Test
    public void getCommonPriceDiscountStock() {
        DiscountCard discountCard = new DiscountCard(1234, 3);
        List<CheckLine> checkLineList = new ArrayList<>();
        checkLineList.add(new CheckLine(6, new Product(0, "Капучино в кофейне ", 4.45, true), 26.7));
        checkLineList.add(new CheckLine(6, new Product(1, "Молоко (1 литр) ", 2, true), 12));
        Check check = new Check(checkLineList, discountCard);
        double commonPriceCheck = new ServiceCheckImpl().getCommonPriceDiscountStock(check);
        assertEquals(commonPriceCheck, 3.87);

    }
    @Test
    public void getCommonPriceDiscountStock1() {
        List<CheckLine> checkLineList = new ArrayList<>();
        checkLineList.add(new CheckLine(6, new Product(0, "Капучино в кофейне ", 4.45, true), 26.7));
        checkLineList.add(new CheckLine(6, new Product(1, "Молоко (1 литр) ", 2, true), 12));
        Check check = new Check(checkLineList);
        double commonPriceCheck = new ServiceCheckImpl().getCommonPriceDiscountStock(check);
        assertEquals(commonPriceCheck, 3.87);

    }
}