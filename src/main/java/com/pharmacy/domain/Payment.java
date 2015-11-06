package com.pharmacy.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "payment")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "shipping")
    private Double shipping;

    @Column(name = "logo_url")
    private String logoURL;

    @Column(name = "total_evaluation_points")
    private Integer totalEvaluationPoints;

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

    public Double getShipping() {
        return shipping;
    }

    public void setShipping(Double shipping) {
        this.shipping = shipping;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public Integer getTotalEvaluationPoints() {
        return totalEvaluationPoints;
    }

    public void setTotalEvaluationPoints(Integer totalEvaluationPoints) {
        this.totalEvaluationPoints = totalEvaluationPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Payment payment = (Payment) o;

        if ( ! Objects.equals(id, payment.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Payment{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", shipping='" + shipping + "'" +
            ", logoURL='" + logoURL + "'" +
            ", totalEvaluationPoints='" + totalEvaluationPoints + "'" +
            '}';
    }
}
