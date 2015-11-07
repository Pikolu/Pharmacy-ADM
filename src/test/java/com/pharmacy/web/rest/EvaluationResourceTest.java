package com.pharmacy.web.rest;

import com.pharmacy.Application;
import com.pharmacy.domain.Evaluation;
import com.pharmacy.repository.EvaluationRepository;
import com.pharmacy.repository.search.EvaluationSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the EvaluationResource REST controller.
 *
 * @see EvaluationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EvaluationResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Float DEFAULT_POINTS = 5F;
    private static final Float UPDATED_POINTS = 4F;

    private static final Integer DEFAULT_DESCRIPTION_POINTS = 5;
    private static final Integer UPDATED_DESCRIPTION_POINTS = 4;

    private static final Integer DEFAULT_SHIPPING_POINTS = 5;
    private static final Integer UPDATED_SHIPPING_POINTS = 4;

    private static final Integer DEFAULT_SHIPPING_PRICE_POINTS = 5;
    private static final Integer UPDATED_SHIPPING_PRICE_POINTS = 4;

    @Inject
    private EvaluationRepository evaluationRepository;

    @Inject
    private EvaluationSearchRepository evaluationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEvaluationMockMvc;

    private Evaluation evaluation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EvaluationResource evaluationResource = new EvaluationResource();
        ReflectionTestUtils.setField(evaluationResource, "evaluationRepository", evaluationRepository);
        ReflectionTestUtils.setField(evaluationResource, "evaluationSearchRepository", evaluationSearchRepository);
        this.restEvaluationMockMvc = MockMvcBuilders.standaloneSetup(evaluationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        evaluation = new Evaluation();
        evaluation.setName(DEFAULT_NAME);
        evaluation.setDescription(DEFAULT_DESCRIPTION);
        evaluation.setPoints(DEFAULT_POINTS);
        evaluation.setDescriptionPoints(DEFAULT_DESCRIPTION_POINTS);
        evaluation.setShippingPoints(DEFAULT_SHIPPING_POINTS);
        evaluation.setShippingPricePoints(DEFAULT_SHIPPING_PRICE_POINTS);
    }

    @Test
    @Transactional
    public void createEvaluation() throws Exception {
        int databaseSizeBeforeCreate = evaluationRepository.findAll().size();

        // Create the Evaluation

        restEvaluationMockMvc.perform(post("/api/evaluations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(evaluation)))
                .andExpect(status().isCreated());

        // Validate the Evaluation in the database
        List<Evaluation> evaluations = evaluationRepository.findAll();
        assertThat(evaluations).hasSize(databaseSizeBeforeCreate + 1);
        Evaluation testEvaluation = evaluations.get(evaluations.size() - 1);
        assertThat(testEvaluation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEvaluation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEvaluation.getPoints()).isEqualTo(DEFAULT_POINTS);
        assertThat(testEvaluation.getDescriptionPoints()).isEqualTo(DEFAULT_DESCRIPTION_POINTS);
        assertThat(testEvaluation.getShippingPoints()).isEqualTo(DEFAULT_SHIPPING_POINTS);
        assertThat(testEvaluation.getShippingPricePoints()).isEqualTo(DEFAULT_SHIPPING_PRICE_POINTS);
    }

    @Test
    @Transactional
    public void getAllEvaluations() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluations
        restEvaluationMockMvc.perform(get("/api/evaluations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(evaluation.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS.doubleValue())))
                .andExpect(jsonPath("$.[*].descriptionPoints").value(hasItem(DEFAULT_DESCRIPTION_POINTS)))
                .andExpect(jsonPath("$.[*].shippingPoints").value(hasItem(DEFAULT_SHIPPING_POINTS)))
                .andExpect(jsonPath("$.[*].shippingPricePoints").value(hasItem(DEFAULT_SHIPPING_PRICE_POINTS)));
    }

    @Test
    @Transactional
    public void getEvaluation() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get the evaluation
        restEvaluationMockMvc.perform(get("/api/evaluations/{id}", evaluation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(evaluation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS.doubleValue()))
            .andExpect(jsonPath("$.descriptionPoints").value(DEFAULT_DESCRIPTION_POINTS))
            .andExpect(jsonPath("$.shippingPoints").value(DEFAULT_SHIPPING_POINTS))
            .andExpect(jsonPath("$.shippingPricePoints").value(DEFAULT_SHIPPING_PRICE_POINTS));
    }

    @Test
    @Transactional
    public void getNonExistingEvaluation() throws Exception {
        // Get the evaluation
        restEvaluationMockMvc.perform(get("/api/evaluations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvaluation() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

		int databaseSizeBeforeUpdate = evaluationRepository.findAll().size();

        // Update the evaluation
        evaluation.setName(UPDATED_NAME);
        evaluation.setDescription(UPDATED_DESCRIPTION);
        evaluation.setPoints(UPDATED_POINTS);
        evaluation.setDescriptionPoints(UPDATED_DESCRIPTION_POINTS);
        evaluation.setShippingPoints(UPDATED_SHIPPING_POINTS);
        evaluation.setShippingPricePoints(UPDATED_SHIPPING_PRICE_POINTS);

        restEvaluationMockMvc.perform(put("/api/evaluations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(evaluation)))
                .andExpect(status().isOk());

        // Validate the Evaluation in the database
        List<Evaluation> evaluations = evaluationRepository.findAll();
        assertThat(evaluations).hasSize(databaseSizeBeforeUpdate);
        Evaluation testEvaluation = evaluations.get(evaluations.size() - 1);
        assertThat(testEvaluation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEvaluation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEvaluation.getPoints()).isEqualTo(UPDATED_POINTS);
        assertThat(testEvaluation.getDescriptionPoints()).isEqualTo(UPDATED_DESCRIPTION_POINTS);
        assertThat(testEvaluation.getShippingPoints()).isEqualTo(UPDATED_SHIPPING_POINTS);
        assertThat(testEvaluation.getShippingPricePoints()).isEqualTo(UPDATED_SHIPPING_PRICE_POINTS);
    }

    @Test
    @Transactional
    public void deleteEvaluation() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

		int databaseSizeBeforeDelete = evaluationRepository.findAll().size();

        // Get the evaluation
        restEvaluationMockMvc.perform(delete("/api/evaluations/{id}", evaluation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Evaluation> evaluations = evaluationRepository.findAll();
        assertThat(evaluations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
