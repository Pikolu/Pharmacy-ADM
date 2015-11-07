package com.pharmacy.web.rest;

import com.pharmacy.Application;
import com.pharmacy.domain.Pharmacy;
import com.pharmacy.repository.PharmacyRepository;
import com.pharmacy.repository.search.PharmacySearchRepository;

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
 * Test class for the PharmacyResource REST controller.
 *
 * @see PharmacyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PharmacyResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Double DEFAULT_SHIPPING = 1D;
    private static final Double UPDATED_SHIPPING = 2D;
    private static final String DEFAULT_LOGO_URL = "AAAAA";
    private static final String UPDATED_LOGO_URL = "BBBBB";

    private static final Integer DEFAULT_TOTAL_EVALUATION_POINTS = 1;
    private static final Integer UPDATED_TOTAL_EVALUATION_POINTS = 2;

    @Inject
    private PharmacyRepository pharmacyRepository;

    @Inject
    private PharmacySearchRepository pharmacySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPharmacyMockMvc;

    private Pharmacy pharmacy;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PharmacyResource pharmacyResource = new PharmacyResource();
        ReflectionTestUtils.setField(pharmacyResource, "pharmacyRepository", pharmacyRepository);
        ReflectionTestUtils.setField(pharmacyResource, "pharmacySearchRepository", pharmacySearchRepository);
        this.restPharmacyMockMvc = MockMvcBuilders.standaloneSetup(pharmacyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pharmacy = new Pharmacy();
        pharmacy.setName(DEFAULT_NAME);
        pharmacy.setShipping(DEFAULT_SHIPPING);
        pharmacy.setLogoURL(DEFAULT_LOGO_URL);
        pharmacy.setTotalEvaluationPoints(DEFAULT_TOTAL_EVALUATION_POINTS);
    }

    @Test
    @Transactional
    public void createPharmacy() throws Exception {
        int databaseSizeBeforeCreate = pharmacyRepository.findAll().size();

        // Create the Pharmacy

        restPharmacyMockMvc.perform(post("/api/pharmacys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pharmacy)))
                .andExpect(status().isCreated());

        // Validate the Pharmacy in the database
        List<Pharmacy> pharmacys = pharmacyRepository.findAll();
        assertThat(pharmacys).hasSize(databaseSizeBeforeCreate + 1);
        Pharmacy testPharmacy = pharmacys.get(pharmacys.size() - 1);
        assertThat(testPharmacy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPharmacy.getShipping()).isEqualTo(DEFAULT_SHIPPING);
        assertThat(testPharmacy.getLogoURL()).isEqualTo(DEFAULT_LOGO_URL);
        assertThat(testPharmacy.getTotalEvaluationPoints()).isEqualTo(DEFAULT_TOTAL_EVALUATION_POINTS);
    }

    @Test
    @Transactional
    public void getAllPharmacys() throws Exception {
        // Initialize the database
        pharmacyRepository.saveAndFlush(pharmacy);

        // Get all the pharmacys
        restPharmacyMockMvc.perform(get("/api/pharmacys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pharmacy.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].shipping").value(hasItem(DEFAULT_SHIPPING.doubleValue())))
                .andExpect(jsonPath("$.[*].logoURL").value(hasItem(DEFAULT_LOGO_URL.toString())))
                .andExpect(jsonPath("$.[*].totalEvaluationPoints").value(hasItem(DEFAULT_TOTAL_EVALUATION_POINTS)));
    }

    @Test
    @Transactional
    public void getPharmacy() throws Exception {
        // Initialize the database
        pharmacyRepository.saveAndFlush(pharmacy);

        // Get the pharmacy
        restPharmacyMockMvc.perform(get("/api/pharmacys/{id}", pharmacy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pharmacy.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.shipping").value(DEFAULT_SHIPPING.doubleValue()))
            .andExpect(jsonPath("$.logoURL").value(DEFAULT_LOGO_URL.toString()))
            .andExpect(jsonPath("$.totalEvaluationPoints").value(DEFAULT_TOTAL_EVALUATION_POINTS));
    }

    @Test
    @Transactional
    public void getNonExistingPharmacy() throws Exception {
        // Get the pharmacy
        restPharmacyMockMvc.perform(get("/api/pharmacys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePharmacy() throws Exception {
        // Initialize the database
        pharmacyRepository.saveAndFlush(pharmacy);

		int databaseSizeBeforeUpdate = pharmacyRepository.findAll().size();

        // Update the pharmacy
        pharmacy.setName(UPDATED_NAME);
        pharmacy.setShipping(UPDATED_SHIPPING);
        pharmacy.setLogoURL(UPDATED_LOGO_URL);
        pharmacy.setTotalEvaluationPoints(UPDATED_TOTAL_EVALUATION_POINTS);

        restPharmacyMockMvc.perform(put("/api/pharmacys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pharmacy)))
                .andExpect(status().isOk());

        // Validate the Pharmacy in the database
        List<Pharmacy> pharmacys = pharmacyRepository.findAll();
        assertThat(pharmacys).hasSize(databaseSizeBeforeUpdate);
        Pharmacy testPharmacy = pharmacys.get(pharmacys.size() - 1);
        assertThat(testPharmacy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPharmacy.getShipping()).isEqualTo(UPDATED_SHIPPING);
        assertThat(testPharmacy.getLogoURL()).isEqualTo(UPDATED_LOGO_URL);
        assertThat(testPharmacy.getTotalEvaluationPoints()).isEqualTo(UPDATED_TOTAL_EVALUATION_POINTS);
    }

    @Test
    @Transactional
    public void deletePharmacy() throws Exception {
        // Initialize the database
        pharmacyRepository.saveAndFlush(pharmacy);

		int databaseSizeBeforeDelete = pharmacyRepository.findAll().size();

        // Get the pharmacy
        restPharmacyMockMvc.perform(delete("/api/pharmacys/{id}", pharmacy.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Pharmacy> pharmacys = pharmacyRepository.findAll();
        assertThat(pharmacys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
