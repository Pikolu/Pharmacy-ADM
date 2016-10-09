package com.pharmacy.service;

import com.pharmacy.domain.Article;
import com.pharmacy.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by Alex Tina on 04.10.2016.
 */
@Service
@Transactional
public class ArticleService {

    @Inject
    private ArticleRepository articleRepository;

    public Article save(Article article) {
        return articleRepository.saveAndFlush(article);
    }

}
