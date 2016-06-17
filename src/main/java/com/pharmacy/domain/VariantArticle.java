package com.pharmacy.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * A Article.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class VariantArticle extends Article {

    @ManyToOne(cascade = CascadeType.ALL)
    private Article parent;

    @Column(name = "variant_code")
    private Integer variantCode;


    public Article getParent() {
        return parent;
    }

    public void setParent(Article parent) {
        this.parent = parent;
    }

    public Integer getVariantCode() {
        return variantCode;
    }

    public void setVariantCode(Integer variantCode) {
        this.variantCode = variantCode;
    }
}
