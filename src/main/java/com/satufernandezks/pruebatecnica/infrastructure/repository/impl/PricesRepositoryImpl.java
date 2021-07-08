package com.satufernandezks.pruebatecnica.infrastructure.repository.impl;

import com.satufernandezks.pruebatecnica.infrastructure.entities.PricesEntity;
import com.satufernandezks.pruebatecnica.infrastructure.repository.PricesRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PricesRepositoryImpl implements PricesRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PricesEntity getPrice(LocalDateTime applicationDate, Integer productId, Integer brandId) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PricesEntity> criteriaQuery = criteriaBuilder.createQuery(PricesEntity.class);
        Root<PricesEntity> entityRoot = criteriaQuery.from(PricesEntity.class);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<PricesEntity> subRoot = subquery.from(PricesEntity.class);
        List<Predicate> predicatesSubquery = new ArrayList<>();
        predicatesSubquery.add(criteriaBuilder.lessThanOrEqualTo(subRoot.get("startDate"), applicationDate));
        predicatesSubquery.add(criteriaBuilder.greaterThanOrEqualTo(subRoot.get("endDate"), applicationDate));
        predicatesSubquery.add(criteriaBuilder.equal(subRoot.get("productId"), productId));
        predicatesSubquery.add(criteriaBuilder.equal(subRoot.get("brandId"), brandId));
        subquery.select(criteriaBuilder.max(subRoot.get("priority")));
        subquery.where(criteriaBuilder.and(predicatesSubquery.toArray(new Predicate[predicatesSubquery.size()])));

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.lessThanOrEqualTo(entityRoot.get("startDate"), applicationDate));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(entityRoot.get("endDate"), applicationDate));
        predicates.add(criteriaBuilder.equal(entityRoot.get("productId"), productId));
        predicates.add(criteriaBuilder.equal(entityRoot.get("brandId"), brandId));
        predicates.add(criteriaBuilder.equal(entityRoot.get("priority"), subquery));
        criteriaQuery.select(criteriaBuilder.construct(PricesEntity.class, entityRoot.get("brandId"), entityRoot.get("startDate"),
                entityRoot.get("endDate"), entityRoot.get("priceList"), entityRoot.get("productId"), entityRoot.get("price")))
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

        List<PricesEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        return resultList.isEmpty() ? null : resultList.get(0);
    }
}
