package com.pharmacy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A Article.
 */
@Entity
@Table(name = "article", indexes = {
    @Index(name = "article_number_index", columnList="articel_number")
})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "article")
public class Article implements Serializable {

    @Id
    private Long id;

    @Column(name = "name", length = 500)
    @Field(index = FieldIndex.analyzed, type = FieldType.String)
    private String name;

    @Column(name = "sort_name", length = 500)
    @Field(index = FieldIndex.not_analyzed, type = FieldType.String)
    private String sortName;

    @Size(max = 40000)
    @Column(name = "description", length = 40000)
    private String description;

    @Size(max = 40000)
    @Column(name = "full_description", length = 40000)
    private String fullDescription;

    @NotNull
    @Column(name = "articel_number", nullable = false)
    private Integer articelNumber;

    @Column(name = "image_url", length = 1000)
    private String imageURL;

    @Column(name = "key_words", length = 1000)
    private String keyWords;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Price> prices = new HashSet<>();

    @Column(name = "exported")
    private Boolean exported;

    @Column(name = "displayed_on_homepage")
    private Boolean displayedOnHomepage;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VariantArticle> variantArticles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getArticelNumber() {
        return articelNumber;
    }

    public void setArticelNumber(Integer articelNumber) {
        this.articelNumber = articelNumber;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public Set<Price> getPrices() {
        return prices;
    }

    public void setPrices(Set<Price> prices) {
        this.prices = prices;
    }

    public Boolean getExported() {
        return exported;
    }

    public void setExported(Boolean exported) {
        this.exported = exported;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public Boolean getDisplayedOnHomepage() {
        return displayedOnHomepage;
    }

    public void setDisplayedOnHomepage(Boolean displayedOnHomepage) {
        this.displayedOnHomepage = displayedOnHomepage;
    }

    public Set<VariantArticle> getVariantArticles() {
        return variantArticles;
    }

    public void setVariantArticles(Set<VariantArticle> variantArticles) {
        this.variantArticles = variantArticles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Article article = (Article) o;

        if (!Objects.equals(id, article.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Article{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", articelNumber='" + articelNumber + "'" +
            ", imageURL='" + imageURL + "'" +
            ", keyWords='" + keyWords + "'" +
            '}';
    }

    public String toInfoString() {
        return "Article{" +
            "id=" + id +
            ", articelNumber='" + articelNumber + "'" +
            ", name='" + name + "'" +
            '}';
    }

}
