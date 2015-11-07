package com.pharmacy.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pharmacy.domain.Pharmacy;
import com.pharmacy.repository.PharmacyRepository;
import com.pharmacy.repository.search.PharmacySearchRepository;
import com.pharmacy.web.rest.util.HeaderUtil;
import com.pharmacy.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Pharmacy.
 */
@RestController
@RequestMapping("/api")
public class PharmacyResource {

    private final Logger log = LoggerFactory.getLogger(PharmacyResource.class);

    @Inject
    private PharmacyRepository pharmacyRepository;

    @Inject
    private PharmacySearchRepository pharmacySearchRepository;

    /**
     * POST  /pharmacys -> Create a new pharmacy.
     */
    @RequestMapping(value = "/pharmacys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pharmacy> createPharmacy(@RequestBody Pharmacy pharmacy) throws URISyntaxException {
        log.debug("REST request to save Pharmacy : {}", pharmacy);
        if (pharmacy.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pharmacy cannot already have an ID").body(null);
        }
        Pharmacy result = pharmacyRepository.save(pharmacy);
        pharmacySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pharmacys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pharmacy", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pharmacys -> Updates an existing pharmacy.
     */
    @RequestMapping(value = "/pharmacys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pharmacy> updatePharmacy(@RequestBody Pharmacy pharmacy) throws URISyntaxException {
        log.debug("REST request to update Pharmacy : {}", pharmacy);
        if (pharmacy.getId() == null) {
            return createPharmacy(pharmacy);
        }
        Pharmacy result = pharmacyRepository.save(pharmacy);
        pharmacySearchRepository.save(pharmacy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pharmacy", pharmacy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pharmacys -> get all the pharmacys.
     */
    @RequestMapping(value = "/pharmacys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Pharmacy>> getAllPharmacys(Pageable pageable)
        throws URISyntaxException {
        Page<Pharmacy> page = pharmacyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pharmacys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pharmacys/:id -> get the "id" pharmacy.
     */
    @RequestMapping(value = "/pharmacys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pharmacy> getPharmacy(@PathVariable Long id) {
        log.debug("REST request to get Pharmacy : {}", id);
        return Optional.ofNullable(pharmacyRepository.findOneWithEagerRelationships(id))
            .map(pharmacy -> new ResponseEntity<>(
                pharmacy,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pharmacys/:id -> delete the "id" pharmacy.
     */
    @RequestMapping(value = "/pharmacys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePharmacy(@PathVariable Long id) {
        log.debug("REST request to delete Pharmacy : {}", id);
        pharmacyRepository.delete(id);
        pharmacySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pharmacy", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pharmacys/:query -> search for the pharmacy corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pharmacys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Pharmacy> searchPharmacys(@PathVariable String query) {
        return StreamSupport
            .stream(pharmacySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
