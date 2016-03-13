package com.pharmacy.domain;

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
 * A Evaluation.
 */
@Entity
@Table(name = "evaluation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "evaluation")
public class Evaluation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Size(max = 4000)
    @Column(name = "description", length = 4000)
    private String description;

    @Max(value = 5)
    @Column(name = "points")
    private Float points;

    @Max(value = 5)
    @Column(name = "description_points")
    private Integer descriptionPoints;

    @Max(value = 5)
    @Column(name = "shipping_points")
    private Integer shippingPoints;

    @Max(value = 5)
    @Column(name = "shipping_price_points")
    private Integer shippingPricePoints;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(cascade = CascadeType.ALL)
    private Pharmacy pharmacy;

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

    public Float getPoints() {
        return points;
    }

    public void setPoints(Float points) {
        this.points = points;
    }

    public Integer getDescriptionPoints() {
        return descriptionPoints;
    }

    public void setDescriptionPoints(Integer descriptionPoints) {
        this.descriptionPoints = descriptionPoints;
    }

    public Integer getShippingPoints() {
        return shippingPoints;
    }

    public void setShippingPoints(Integer shippingPoints) {
        this.shippingPoints = shippingPoints;
    }

    public Integer getShippingPricePoints() {
        return shippingPricePoints;
    }

    public void setShippingPricePoints(Integer shippingPricePoints) {
        this.shippingPricePoints = shippingPricePoints;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Evaluation evaluation = (Evaluation) o;

        if ( ! Objects.equals(id, evaluation.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Evaluation{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", points='" + points + "'" +
            ", descriptionPoints='" + descriptionPoints + "'" +
            ", shippingPoints='" + shippingPoints + "'" +
            ", shippingPricePoints='" + shippingPricePoints + "'" +
            '}';
    }
}
