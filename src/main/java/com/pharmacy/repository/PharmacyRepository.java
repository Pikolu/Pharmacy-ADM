package com.pharmacy.repository;

import com.pharmacy.domain.Pharmacy;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Pharmacy entity.
 */
public interface PharmacyRepository extends JpaRepository<Pharmacy,Long> {

    @Query("select distinct pharmacy from Pharmacy pharmacy left join fetch pharmacy.payments")
    List<Pharmacy> findAllWithEagerRelationships();

    @Query("select pharmacy from Pharmacy pharmacy left join fetch pharmacy.payments where pharmacy.id =:id")
    Pharmacy findOneWithEagerRelationships(@Param("id") Long id);

}
