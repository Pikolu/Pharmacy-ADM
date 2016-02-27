package com.pharmacy.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Price.
 */
@Entity
@Table(name = "price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "price")
public class Price implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "suggested_retail_price")
    private Float suggestedRetailPrice;

    @Column(name = "extra_shipping_suffix")
    private String extraShippingSuffix;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "price")
    private Double price;

    @OneToOne
    private Pharmacy pharmacy;

    @ManyToOne
    private Article article;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getSuggestedRetailPrice() {
        return suggestedRetailPrice;
    }

    public void setSuggestedRetailPrice(Float suggestedRetailPrice) {
        this.suggestedRetailPrice = suggestedRetailPrice;
    }

    public String getExtraShippingSuffix() {
        return extraShippingSuffix;
    }

    public void setExtraShippingSuffix(String extraShippingSuffix) {
        this.extraShippingSuffix = extraShippingSuffix;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Price price = (Price) o;

        if (!Objects.equals(id, price.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Price{" +
            "id=" + id +
            ", suggestedRetailPrice='" + suggestedRetailPrice + "'" +
            ", extraShippingSuffix='" + extraShippingSuffix + "'" +
            ", discount='" + discount + "'" +
            ", price='" + price + "'" +
            '}';
    }
}
