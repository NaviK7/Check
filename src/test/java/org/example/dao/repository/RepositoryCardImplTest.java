package org.example.dao.repository;

import org.example.dao.model.DiscountCard;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class RepositoryCardImplTest {


    @Test
    public void findOneDiscountCard() {
        DiscountCard discountCard = new DiscountCard(1234, 3);
        Optional<DiscountCard> discountCardOptional = new RepositoryCardImpl().findOneDiscountCard(1234);
        DiscountCard discountCardTest = discountCardOptional.get();
        assertEquals(discountCardTest, discountCard);
    }
}