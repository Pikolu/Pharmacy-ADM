package com.pharmacy.repository;

import com.pharmacy.domain.Article;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Pharmacy GmbH
 * Created by Alexander on 13.03.2016.
 */
@Repository
@Transactional
public class ArticleRepositoryImpl {

    private final EntityManager entityManager;

    @Inject
    public ArticleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Article article) {
        entityManager.merge(article);
    }

    public void save(List<Article> articles) {
        articles.forEach(this::save);
    }

    public List<Article> findArticles(String query) {
        return createLikeIdQuery(query).getResultList();
    }

    private TypedQuery<Article> createLikeIdQuery(String query) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
        Root<Article> article = criteriaQuery.from(Article.class);
        if (StringUtils.isNumeric(query)) {
            criteriaQuery.where(criteriaBuilder.or(
                criteriaBuilder.like(article.get("id").as(String.class), "%" + Long.valueOf(query) + "%"),
                criteriaBuilder.like(article.get("articelNumber").as(String.class), "%" + Long.valueOf(query) + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(article.get("name")), "%" + query.toLowerCase() + "%")));
        } else {
            criteriaQuery.where(criteriaBuilder.like(
                criteriaBuilder.lower(article.get("name")), "%" + query.toLowerCase() + "%"));
        }
        return entityManager.createQuery(criteriaQuery);
    }
}
