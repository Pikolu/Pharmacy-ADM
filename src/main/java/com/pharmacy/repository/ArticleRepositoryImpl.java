package com.pharmacy.repository;

import com.pharmacy.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Pharmacy GmbH
 * Created by Alexander on 13.03.2016.
 */
@Repository
@Transactional
public class ArticleRepositoryImpl {

    @Inject
    private EntityManager entityManager;

    public void save(Article article) {
        entityManager.merge(article);
    }

    public void save(List<Article> articles) {
        articles.forEach(a -> save(a));
    }
}
