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
public class Evaluation extends BaseUUID {

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
    public String toString() {
        return "Evaluation{" +
            "id=" + getId() +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", points='" + points + "'" +
            ", descriptionPoints='" + descriptionPoints + "'" +
            ", shippingPoints='" + shippingPoints + "'" +
            ", shippingPricePoints='" + shippingPricePoints + "'" +
            '}';
    }
}
