package com.pharmacy.repository;

import com.pharmacy.domain.Price;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Pharmacy GmbH
 * Created by Alexander on 13.03.2016.
 */
public class PriceRepositoryImpl {

    @Inject
    private EntityManager entityManager;

    public void save(Price price){
        entityManager.merge(price);
    }

    public void save(List<Price> prices){
        prices.forEach(p -> save(p));
    }
}
