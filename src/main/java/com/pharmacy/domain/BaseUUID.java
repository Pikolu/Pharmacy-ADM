package com.pharmacy.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Created by apopow on 27.12.2015.
 */
@MappedSuperclass
public class BaseUUID implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @LastModifiedDate
    @Column(name = "last_updated")
    private ZonedDateTime lastUpdated = ZonedDateTime.now();

    @CreatedDate
    @Column(name = "creation_date")
    private ZonedDateTime creationDate = ZonedDateTime.now();


    public BaseUUID() {

    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    public ZonedDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseUUID other = (BaseUUID) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BaseUUID{" + "id=" + id + ", lastUpdated=" + getLastUpdated() + ", creationDate=" + getCreationDate() + '}';
    }

}
