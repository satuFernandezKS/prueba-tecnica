package com.satufernandezks.pruebatecnica.infrastructure.repository.impl;

import com.satufernandezks.pruebatecnica.infrastructure.entities.PricesEntity;
import com.satufernandezks.pruebatecnica.infrastructure.repository.PricesRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PricesRepositoryImpl implements PricesRepository {

    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";
    private static final String PRODUCT_ID = "productId";
    private static final String PRIORITY = "priority";
    private static final String BRAND_ID = "brandId";
    private static final String PRICE = "price";
    private static final String PRICE_LIST = "priceList";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PricesEntity getPrice(LocalDateTime applicationDate, Integer productId, Integer brandId) {

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PricesEntity> criteriaQuery = criteriaBuilder.createQuery(PricesEntity.class);
        Root<PricesEntity> entityRoot = criteriaQuery.from(PricesEntity.class);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<PricesEntity> subRoot = subquery.from(PricesEntity.class);
        List<Predicate> predicatesSubquery = new ArrayList<>();
        predicatesSubquery.add(criteriaBuilder.lessThanOrEqualTo(subRoot.get(START_DATE), applicationDate));
        predicatesSubquery.add(criteriaBuilder.greaterThanOrEqualTo(subRoot.get(END_DATE), applicationDate));
        predicatesSubquery.add(criteriaBuilder.equal(subRoot.get(PRODUCT_ID), productId));
        predicatesSubquery.add(criteriaBuilder.equal(subRoot.get(BRAND_ID), brandId));
        subquery.select(criteriaBuilder.max(subRoot.get(PRIORITY)));
        subquery.where(criteriaBuilder.and(predicatesSubquery.toArray(new Predicate[predicatesSubquery.size()])));

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.lessThanOrEqualTo(entityRoot.get(START_DATE), applicationDate));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(entityRoot.get(END_DATE), applicationDate));
        predicates.add(criteriaBuilder.equal(entityRoot.get(PRODUCT_ID), productId));
        predicates.add(criteriaBuilder.equal(entityRoot.get(BRAND_ID), brandId));
        predicates.add(criteriaBuilder.equal(entityRoot.get(PRIORITY), subquery));
        criteriaQuery.select(criteriaBuilder.construct(PricesEntity.class, entityRoot.get(BRAND_ID), entityRoot.get(START_DATE),
                entityRoot.get(END_DATE), entityRoot.get(PRICE_LIST), entityRoot.get(PRODUCT_ID), entityRoot.get(PRICE)))
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

        List<PricesEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        return resultList.isEmpty() ? null : resultList.get(0);
    }
}
