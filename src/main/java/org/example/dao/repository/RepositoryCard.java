package org.example.dao.repository;

import org.example.dao.model.DiscountCard;

import java.util.List;
import java.util.Optional;

public interface RepositoryCard {
    public List<DiscountCard> getAllDiscountCard();


    public Optional<DiscountCard> findOneDiscountCard(int number);
}
