package com.pharmacy.repository;

import com.pharmacy.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Article entity.
 */
public interface ArticleRepository extends JpaRepository<Article,Long> {

    @Query("SELECT DISTINCT a FROM Article a WHERE a.articelNumber = :articelNumber")
    Article findArticleByArticleNumber(@Param(value = "articelNumber") Integer articleNumber);

    @Query("SELECT a FROM Article a WHERE a.baseArticle IS NULL")
    Page<Article> findArticleWIthoutVariant(Pageable pageable);
}
