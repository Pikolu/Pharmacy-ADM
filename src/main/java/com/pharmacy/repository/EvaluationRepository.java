package com.pharmacy.repository;

import com.pharmacy.domain.Evaluation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Evaluation entity.
 */
public interface EvaluationRepository extends JpaRepository<Evaluation,Long> {

}
