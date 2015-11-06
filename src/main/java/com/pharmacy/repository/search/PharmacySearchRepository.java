package com.pharmacy.repository.search;

import com.pharmacy.domain.Pharmacy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Pharmacy entity.
 */
public interface PharmacySearchRepository extends ElasticsearchRepository<Pharmacy, Long> {
}
