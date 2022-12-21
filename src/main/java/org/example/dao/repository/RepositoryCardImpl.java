package org.example.dao.repository;

import file.ReadFileCard;
import file.ReadFileProduct;
import org.example.dao.model.DiscountCard;
import org.example.dao.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryCardImpl implements RepositoryCard {
    private final List<DiscountCard> discountCardList = new ArrayList<>();
    private static final ReadFileCard READ_FILE = new ReadFileCard();

    public List<DiscountCard> getAllDiscountCard() {
        READ_FILE.readLines();
        String text = READ_FILE.readLines();
        text = text.replaceAll("\\s+", " ");
        String[] cards = text.split(";");
        for (int i = 0; i < cards.length; i++) {
            String[] product = cards[i].split(",");
            for (int j = 0; j < 1; j++) {
                discountCardList.add(new DiscountCard(Integer.parseInt(product[j]), Integer.parseInt(product[j + 1])));
            }
        }
        return discountCardList;
    }



    public Optional<DiscountCard> findOneDiscountCard(int number) {
        try {
            for (DiscountCard discountCard : getAllDiscountCard()) {
                if (discountCard.getNumber() == number) {
                    return Optional.of(discountCard);
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Такой карты нет");
        }
        return Optional.empty();
    }
}
