package com.pharmacy.repository;

import com.pharmacy.domain.Article;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Article entity.
 */
public interface ArticleRepository extends JpaRepository<Article,Long> {

    @Query("SELECT DISTINCT a FROM Article a WHERE a.articelNumber = :articelNumber")
    Article findArticleByArticleNumber(@Param(value = "articelNumber") Integer articleNumber);

    @Query("SELECT DISTINCT a FROM Article a WHERE a.articelNumber = :articelNumber and a.parent = :parent")
    Article findParentArticle(@Param(value = "articelNumber") Integer articleNumber, @Param(value = "parent") Boolean parent);
}
