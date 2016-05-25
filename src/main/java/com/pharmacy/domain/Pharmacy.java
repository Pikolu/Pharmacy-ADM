package com.pharmacy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Pharmacy.
 */
@Entity
@Table(name = "pharmacy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pharmacy")
public class Pharmacy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    @Field(index = FieldIndex.not_analyzed, type = FieldType.String)
    private String name;

    @Column(name = "display_name")
    @Field(index = FieldIndex.not_analyzed, type = FieldType.String)
    private String displayName;

    @Column(name = "owner")
    @Field(index = FieldIndex.not_analyzed, type = FieldType.String)
    private String owner;

    @Column(name = "street", length = 100)
    @Field(type = FieldType.String)
    private String street;

    @Column(name = "house_number", length = 100)
    @Field(type = FieldType.String)
    private String houseNumber;

    @Column(name = "zip_code", length = 10)
    @Field(type = FieldType.String)
    private String zipCode;

    @Column(name = "city", length = 100)
    @Field(type = FieldType.String)
    private String city;

    @Column(name = "phone_number", length = 100)
    @Field(type = FieldType.String)
    private String phoneNumber;

    @Column(name = "fax", length = 100)
    @Field(type = FieldType.String)
    private String fax;

    @Column(name = "email", length = 255)
    @Field(type = FieldType.String)
    private String email;

    @Column(name = "home_page", length = 255)
    @Field(type = FieldType.String)
    private String homePage;

    @Column(name = "shipping")
    private Double shipping;

    @Column(name = "free_shipping")
    private Double freeShipping;

    @Column(name = "logo_url")
    private String logoURL;

    @Column(name = "total_evaluation_points")
    private Integer totalEvaluationPoints;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "pharmacy_payment",
        joinColumns = @JoinColumn(name = "pharmacys_id", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "payments_id", referencedColumnName = "ID"))
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "pharmacy", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Evaluation> evaluations = new HashSet<>();

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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public Double getShipping() {
        return shipping;
    }

    public void setShipping(Double shipping) {
        this.shipping = shipping;
    }

    public Double getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(Double freeShipping) {
        this.freeShipping = freeShipping;
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

    public Set<Payment> getPayments() {
        if (payments == null) {
            payments = new HashSet<>();
        }
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Set<Evaluation> getEvaluations() {
        if (evaluations == null) {
            evaluations = new HashSet<>();
        }
        return evaluations;
    }

    public void setEvaluations(Set<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pharmacy pharmacy = (Pharmacy) o;

        return Objects.equals(id, pharmacy.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", shipping='" + shipping + "'" +
            ", logoURL='" + logoURL + "'" +
            ", totalEvaluationPoints='" + totalEvaluationPoints + "'" +
            '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
