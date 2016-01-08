package com.pharmacy.web.rest;

import com.pharmacy.Application;
import com.pharmacy.domain.Price;
import com.pharmacy.repository.PriceRepository;
import com.pharmacy.repository.search.PriceSearchRepository;

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
 * Test class for the PriceResource REST controller.
 *
 * @see PriceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PriceResourceTest {


    private static final Float DEFAULT_SUGGESTED_RETAIL_PRICE = 1F;
    private static final Float UPDATED_SUGGESTED_RETAIL_PRICE = 2F;
    private static final String DEFAULT_EXTRA_SHIPPING_SUFFIX = "AAAAA";
    private static final String UPDATED_EXTRA_SHIPPING_SUFFIX = "BBBBB";

    private static final Integer DEFAULT_DISCOUNT = 1;
    private static final Integer UPDATED_DISCOUNT = 2;

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    @Inject
    private PriceRepository priceRepository;

    @Inject
    private PriceSearchRepository priceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPriceMockMvc;

    private Price price;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PriceResource priceResource = new PriceResource();
        ReflectionTestUtils.setField(priceResource, "priceRepository", priceRepository);
        ReflectionTestUtils.setField(priceResource, "priceSearchRepository", priceSearchRepository);
        this.restPriceMockMvc = MockMvcBuilders.standaloneSetup(priceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        price = new Price();
        price.setSuggestedRetailPrice(DEFAULT_SUGGESTED_RETAIL_PRICE);
        price.setExtraShippingSuffix(DEFAULT_EXTRA_SHIPPING_SUFFIX);
        price.setDiscount(DEFAULT_DISCOUNT);
        price.setPrice(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createPrice() throws Exception {
        int databaseSizeBeforeCreate = priceRepository.findAll().size();

        // Create the Price

        restPriceMockMvc.perform(post("/api/prices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(price)))
                .andExpect(status().isCreated());

        // Validate the Price in the database
        List<Price> prices = priceRepository.findAll();
        assertThat(prices).hasSize(databaseSizeBeforeCreate + 1);
        Price testPrice = prices.get(prices.size() - 1);
        assertThat(testPrice.getSuggestedRetailPrice()).isEqualTo(DEFAULT_SUGGESTED_RETAIL_PRICE);
        assertThat(testPrice.getExtraShippingSuffix()).isEqualTo(DEFAULT_EXTRA_SHIPPING_SUFFIX);
        assertThat(testPrice.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testPrice.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void getAllPrices() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the prices
        restPriceMockMvc.perform(get("/api/prices"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(price.getId().intValue())))
                .andExpect(jsonPath("$.[*].suggestedRetailPrice").value(hasItem(DEFAULT_SUGGESTED_RETAIL_PRICE.doubleValue())))
                .andExpect(jsonPath("$.[*].extraShippingSuffix").value(hasItem(DEFAULT_EXTRA_SHIPPING_SUFFIX.toString())))
                .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT)))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getPrice() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get the price
        restPriceMockMvc.perform(get("/api/prices/{id}", price.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(price.getId().intValue()))
            .andExpect(jsonPath("$.suggestedRetailPrice").value(DEFAULT_SUGGESTED_RETAIL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.extraShippingSuffix").value(DEFAULT_EXTRA_SHIPPING_SUFFIX.toString()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrice() throws Exception {
        // Get the price
        restPriceMockMvc.perform(get("/api/prices/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrice() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

		int databaseSizeBeforeUpdate = priceRepository.findAll().size();

        // Update the price
        price.setSuggestedRetailPrice(UPDATED_SUGGESTED_RETAIL_PRICE);
        price.setExtraShippingSuffix(UPDATED_EXTRA_SHIPPING_SUFFIX);
        price.setDiscount(UPDATED_DISCOUNT);
        price.setPrice(UPDATED_PRICE);

        restPriceMockMvc.perform(put("/api/prices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(price)))
                .andExpect(status().isOk());

        // Validate the Price in the database
        List<Price> prices = priceRepository.findAll();
        assertThat(prices).hasSize(databaseSizeBeforeUpdate);
        Price testPrice = prices.get(prices.size() - 1);
        assertThat(testPrice.getSuggestedRetailPrice()).isEqualTo(UPDATED_SUGGESTED_RETAIL_PRICE);
        assertThat(testPrice.getExtraShippingSuffix()).isEqualTo(UPDATED_EXTRA_SHIPPING_SUFFIX);
        assertThat(testPrice.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testPrice.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void deletePrice() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

		int databaseSizeBeforeDelete = priceRepository.findAll().size();

        // Get the price
        restPriceMockMvc.perform(delete("/api/prices/{id}", price.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Price> prices = priceRepository.findAll();
        assertThat(prices).hasSize(databaseSizeBeforeDelete - 1);
    }
}
