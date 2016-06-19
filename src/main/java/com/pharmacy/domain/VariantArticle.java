package com.pharmacy.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Created by Alex Tina on 18.06.2016.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VariantArticle extends Article {

    @Column(name = "variant_code")
    private Integer variantCode;

    public Integer getVariantCode() {
        return variantCode;
    }

    public void setVariantCode(Integer variantCode) {
        this.variantCode = variantCode;
    }
}
