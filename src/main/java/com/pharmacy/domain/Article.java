package com.pharmacy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "article")
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Size(max = 4000)
    @Column(name = "description", length = 4000)
    private String description;

    @NotNull
    @Column(name = "articel_number", nullable = false)
    private Integer articelNumber;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "deep_link")
    private String deepLink;

    @Column(name = "key_words")
    private String keyWords;

    @OneToMany(mappedBy = "article")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Price> prices = new HashSet<>();

    @Column(name = "exported")
    private Boolean exported;

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

    public String getDeepLink() {
        return deepLink;
    }

    public void setDeepLink(String deepLink) {
        this.deepLink = deepLink;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Article article = (Article) o;

        if ( ! Objects.equals(id, article.id)) return false;

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
            ", deepLink='" + deepLink + "'" +
            ", keyWords='" + keyWords + "'" +
            '}';
    }
}
