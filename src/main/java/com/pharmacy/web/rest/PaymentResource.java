package com.pharmacy.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pharmacy.domain.Payment;
import com.pharmacy.repository.PaymentRepository;
import com.pharmacy.repository.search.PaymentSearchRepository;
import com.pharmacy.web.rest.util.HeaderUtil;
import com.pharmacy.web.rest.util.PaginationUtil;
import com.pharmacy.web.rest.dto.PaymentDTO;
import com.pharmacy.web.rest.mapper.PaymentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Payment.
 */
@RestController
@RequestMapping("/api")
public class PaymentResource {

    private final Logger log = LoggerFactory.getLogger(PaymentResource.class);

    @Inject
    private PaymentRepository paymentRepository;

    @Inject
    private PaymentMapper paymentMapper;

    @Inject
    private PaymentSearchRepository paymentSearchRepository;

    /**
     * POST  /payments -> Create a new payment.
     */
    @RequestMapping(value = "/payments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) throws URISyntaxException {
        log.debug("REST request to save Payment : {}", paymentDTO);
        if (paymentDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new payment cannot already have an ID").body(null);
        }
        Payment payment = paymentMapper.paymentDTOToPayment(paymentDTO);
        Payment result = paymentRepository.save(payment);
        paymentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("payment", result.getId().toString()))
            .body(paymentMapper.paymentToPaymentDTO(result));
    }

    /**
     * PUT  /payments -> Updates an existing payment.
     */
    @RequestMapping(value = "/payments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PaymentDTO> updatePayment(@RequestBody PaymentDTO paymentDTO) throws URISyntaxException {
        log.debug("REST request to update Payment : {}", paymentDTO);
        if (paymentDTO.getId() == null) {
            return createPayment(paymentDTO);
        }
        Payment payment = paymentMapper.paymentDTOToPayment(paymentDTO);
        Payment result = paymentRepository.save(payment);
        paymentSearchRepository.save(payment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("payment", paymentDTO.getId().toString()))
            .body(paymentMapper.paymentToPaymentDTO(result));
    }

    /**
     * GET  /payments -> get all the payments.
     */
    @RequestMapping(value = "/payments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<PaymentDTO>> getAllPayments(Pageable pageable)
        throws URISyntaxException {
        Page<Payment> page = paymentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payments");
        return new ResponseEntity<>(page.getContent().stream()
            .map(paymentMapper::paymentToPaymentDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /payments/:id -> get the "id" payment.
     */
    @RequestMapping(value = "/payments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PaymentDTO> getPayment(@PathVariable Long id) {
        log.debug("REST request to get Payment : {}", id);
        return Optional.ofNullable(paymentRepository.findOne(id))
            .map(paymentMapper::paymentToPaymentDTO)
            .map(paymentDTO -> new ResponseEntity<>(
                paymentDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /payments/:id -> delete the "id" payment.
     */
    @RequestMapping(value = "/payments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        log.debug("REST request to delete Payment : {}", id);
        paymentRepository.delete(id);
        paymentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("payment", id.toString())).build();
    }

    /**
     * SEARCH  /_search/payments/:query -> search for the payment corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/payments/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PaymentDTO> searchPayments(@PathVariable String query) {
        return StreamSupport
            .stream(paymentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(paymentMapper::paymentToPaymentDTO)
            .collect(Collectors.toList());
    }
}
