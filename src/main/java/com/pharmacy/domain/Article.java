package com.pharmacy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A Article.
 */
@Getter
@Setter
@ToString(exclude = {"baseArticle", "variantArticles"})
@Entity
@Table(name = "article", indexes = {
    @Index(name = "article_number_index", columnList="articel_number")
})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "article")
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Price> prices = new HashSet<>();

    @Column(name = "exported")
    private Boolean exported;

    @Column(name = "showed_on_homepage")
    private Boolean showedOnHomepage;

    @ManyToOne
    @JsonIgnore
    private Article baseArticle;

    @OneToMany(mappedBy="baseArticle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Article> variantArticles;

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

}
