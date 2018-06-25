package com.amogh.lms.web.rest;

import com.amogh.lms.AmoghServerApp;

import com.amogh.lms.domain.FormAttributes;
import com.amogh.lms.repository.FormAttributesRepository;
import com.amogh.lms.service.FormAttributesService;
import com.amogh.lms.service.dto.FormAttributesDTO;
import com.amogh.lms.service.mapper.FormAttributesMapper;
import com.amogh.lms.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.amogh.lms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FormAttributesResource REST controller.
 *
 * @see FormAttributesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmoghServerApp.class)
public class FormAttributesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_MANDATORY = false;
    private static final Boolean UPDATED_IS_MANDATORY = true;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private FormAttributesRepository formAttributesRepository;

    @Autowired
    private FormAttributesMapper formAttributesMapper;

    @Autowired
    private FormAttributesService formAttributesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFormAttributesMockMvc;

    private FormAttributes formAttributes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormAttributesResource formAttributesResource = new FormAttributesResource(formAttributesService);
        this.restFormAttributesMockMvc = MockMvcBuilders.standaloneSetup(formAttributesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormAttributes createEntity(EntityManager em) {
        FormAttributes formAttributes = new FormAttributes()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .isMandatory(DEFAULT_IS_MANDATORY)
            .content(DEFAULT_CONTENT);
        return formAttributes;
    }

    @Before
    public void initTest() {
        formAttributes = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormAttributes() throws Exception {
        int databaseSizeBeforeCreate = formAttributesRepository.findAll().size();

        // Create the FormAttributes
        FormAttributesDTO formAttributesDTO = formAttributesMapper.toDto(formAttributes);
        restFormAttributesMockMvc.perform(post("/api/form-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formAttributesDTO)))
            .andExpect(status().isCreated());

        // Validate the FormAttributes in the database
        List<FormAttributes> formAttributesList = formAttributesRepository.findAll();
        assertThat(formAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        FormAttributes testFormAttributes = formAttributesList.get(formAttributesList.size() - 1);
        assertThat(testFormAttributes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFormAttributes.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFormAttributes.isIsMandatory()).isEqualTo(DEFAULT_IS_MANDATORY);
        assertThat(testFormAttributes.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createFormAttributesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formAttributesRepository.findAll().size();

        // Create the FormAttributes with an existing ID
        formAttributes.setId(1L);
        FormAttributesDTO formAttributesDTO = formAttributesMapper.toDto(formAttributes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormAttributesMockMvc.perform(post("/api/form-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formAttributesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FormAttributes in the database
        List<FormAttributes> formAttributesList = formAttributesRepository.findAll();
        assertThat(formAttributesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = formAttributesRepository.findAll().size();
        // set the field null
        formAttributes.setName(null);

        // Create the FormAttributes, which fails.
        FormAttributesDTO formAttributesDTO = formAttributesMapper.toDto(formAttributes);

        restFormAttributesMockMvc.perform(post("/api/form-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formAttributesDTO)))
            .andExpect(status().isBadRequest());

        List<FormAttributes> formAttributesList = formAttributesRepository.findAll();
        assertThat(formAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = formAttributesRepository.findAll().size();
        // set the field null
        formAttributes.setType(null);

        // Create the FormAttributes, which fails.
        FormAttributesDTO formAttributesDTO = formAttributesMapper.toDto(formAttributes);

        restFormAttributesMockMvc.perform(post("/api/form-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formAttributesDTO)))
            .andExpect(status().isBadRequest());

        List<FormAttributes> formAttributesList = formAttributesRepository.findAll();
        assertThat(formAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsMandatoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = formAttributesRepository.findAll().size();
        // set the field null
        formAttributes.setIsMandatory(null);

        // Create the FormAttributes, which fails.
        FormAttributesDTO formAttributesDTO = formAttributesMapper.toDto(formAttributes);

        restFormAttributesMockMvc.perform(post("/api/form-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formAttributesDTO)))
            .andExpect(status().isBadRequest());

        List<FormAttributes> formAttributesList = formAttributesRepository.findAll();
        assertThat(formAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFormAttributes() throws Exception {
        // Initialize the database
        formAttributesRepository.saveAndFlush(formAttributes);

        // Get all the formAttributesList
        restFormAttributesMockMvc.perform(get("/api/form-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isMandatory").value(hasItem(DEFAULT_IS_MANDATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }

    @Test
    @Transactional
    public void getFormAttributes() throws Exception {
        // Initialize the database
        formAttributesRepository.saveAndFlush(formAttributes);

        // Get the formAttributes
        restFormAttributesMockMvc.perform(get("/api/form-attributes/{id}", formAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formAttributes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.isMandatory").value(DEFAULT_IS_MANDATORY.booleanValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFormAttributes() throws Exception {
        // Get the formAttributes
        restFormAttributesMockMvc.perform(get("/api/form-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormAttributes() throws Exception {
        // Initialize the database
        formAttributesRepository.saveAndFlush(formAttributes);
        int databaseSizeBeforeUpdate = formAttributesRepository.findAll().size();

        // Update the formAttributes
        FormAttributes updatedFormAttributes = formAttributesRepository.findOne(formAttributes.getId());
        // Disconnect from session so that the updates on updatedFormAttributes are not directly saved in db
        em.detach(updatedFormAttributes);
        updatedFormAttributes
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .isMandatory(UPDATED_IS_MANDATORY)
            .content(UPDATED_CONTENT);
        FormAttributesDTO formAttributesDTO = formAttributesMapper.toDto(updatedFormAttributes);

        restFormAttributesMockMvc.perform(put("/api/form-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formAttributesDTO)))
            .andExpect(status().isOk());

        // Validate the FormAttributes in the database
        List<FormAttributes> formAttributesList = formAttributesRepository.findAll();
        assertThat(formAttributesList).hasSize(databaseSizeBeforeUpdate);
        FormAttributes testFormAttributes = formAttributesList.get(formAttributesList.size() - 1);
        assertThat(testFormAttributes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFormAttributes.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFormAttributes.isIsMandatory()).isEqualTo(UPDATED_IS_MANDATORY);
        assertThat(testFormAttributes.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingFormAttributes() throws Exception {
        int databaseSizeBeforeUpdate = formAttributesRepository.findAll().size();

        // Create the FormAttributes
        FormAttributesDTO formAttributesDTO = formAttributesMapper.toDto(formAttributes);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFormAttributesMockMvc.perform(put("/api/form-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formAttributesDTO)))
            .andExpect(status().isCreated());

        // Validate the FormAttributes in the database
        List<FormAttributes> formAttributesList = formAttributesRepository.findAll();
        assertThat(formAttributesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFormAttributes() throws Exception {
        // Initialize the database
        formAttributesRepository.saveAndFlush(formAttributes);
        int databaseSizeBeforeDelete = formAttributesRepository.findAll().size();

        // Get the formAttributes
        restFormAttributesMockMvc.perform(delete("/api/form-attributes/{id}", formAttributes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FormAttributes> formAttributesList = formAttributesRepository.findAll();
        assertThat(formAttributesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormAttributes.class);
        FormAttributes formAttributes1 = new FormAttributes();
        formAttributes1.setId(1L);
        FormAttributes formAttributes2 = new FormAttributes();
        formAttributes2.setId(formAttributes1.getId());
        assertThat(formAttributes1).isEqualTo(formAttributes2);
        formAttributes2.setId(2L);
        assertThat(formAttributes1).isNotEqualTo(formAttributes2);
        formAttributes1.setId(null);
        assertThat(formAttributes1).isNotEqualTo(formAttributes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormAttributesDTO.class);
        FormAttributesDTO formAttributesDTO1 = new FormAttributesDTO();
        formAttributesDTO1.setId(1L);
        FormAttributesDTO formAttributesDTO2 = new FormAttributesDTO();
        assertThat(formAttributesDTO1).isNotEqualTo(formAttributesDTO2);
        formAttributesDTO2.setId(formAttributesDTO1.getId());
        assertThat(formAttributesDTO1).isEqualTo(formAttributesDTO2);
        formAttributesDTO2.setId(2L);
        assertThat(formAttributesDTO1).isNotEqualTo(formAttributesDTO2);
        formAttributesDTO1.setId(null);
        assertThat(formAttributesDTO1).isNotEqualTo(formAttributesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(formAttributesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(formAttributesMapper.fromId(null)).isNull();
    }
}
