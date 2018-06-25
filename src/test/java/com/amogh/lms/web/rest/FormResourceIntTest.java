package com.amogh.lms.web.rest;

import com.amogh.lms.AmoghServerApp;

import com.amogh.lms.domain.Form;
import com.amogh.lms.repository.FormRepository;
import com.amogh.lms.service.FormService;
import com.amogh.lms.service.dto.FormDTO;
import com.amogh.lms.service.mapper.FormMapper;
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
 * Test class for the FormResource REST controller.
 *
 * @see FormResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmoghServerApp.class)
public class FormResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private FormMapper formMapper;

    @Autowired
    private FormService formService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFormMockMvc;

    private Form form;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormResource formResource = new FormResource(formService);
        this.restFormMockMvc = MockMvcBuilders.standaloneSetup(formResource)
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
    public static Form createEntity(EntityManager em) {
        Form form = new Form()
            .name(DEFAULT_NAME);
        return form;
    }

    @Before
    public void initTest() {
        form = createEntity(em);
    }

    @Test
    @Transactional
    public void createForm() throws Exception {
        int databaseSizeBeforeCreate = formRepository.findAll().size();

        // Create the Form
        FormDTO formDTO = formMapper.toDto(form);
        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isCreated());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeCreate + 1);
        Form testForm = formList.get(formList.size() - 1);
        assertThat(testForm.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFormWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formRepository.findAll().size();

        // Create the Form with an existing ID
        form.setId(1L);
        FormDTO formDTO = formMapper.toDto(form);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = formRepository.findAll().size();
        // set the field null
        form.setName(null);

        // Create the Form, which fails.
        FormDTO formDTO = formMapper.toDto(form);

        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isBadRequest());

        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllForms() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList
        restFormMockMvc.perform(get("/api/forms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(form.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get the form
        restFormMockMvc.perform(get("/api/forms/{id}", form.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(form.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingForm() throws Exception {
        // Get the form
        restFormMockMvc.perform(get("/api/forms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);
        int databaseSizeBeforeUpdate = formRepository.findAll().size();

        // Update the form
        Form updatedForm = formRepository.findOne(form.getId());
        // Disconnect from session so that the updates on updatedForm are not directly saved in db
        em.detach(updatedForm);
        updatedForm
            .name(UPDATED_NAME);
        FormDTO formDTO = formMapper.toDto(updatedForm);

        restFormMockMvc.perform(put("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isOk());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate);
        Form testForm = formList.get(formList.size() - 1);
        assertThat(testForm.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingForm() throws Exception {
        int databaseSizeBeforeUpdate = formRepository.findAll().size();

        // Create the Form
        FormDTO formDTO = formMapper.toDto(form);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFormMockMvc.perform(put("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isCreated());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);
        int databaseSizeBeforeDelete = formRepository.findAll().size();

        // Get the form
        restFormMockMvc.perform(delete("/api/forms/{id}", form.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Form.class);
        Form form1 = new Form();
        form1.setId(1L);
        Form form2 = new Form();
        form2.setId(form1.getId());
        assertThat(form1).isEqualTo(form2);
        form2.setId(2L);
        assertThat(form1).isNotEqualTo(form2);
        form1.setId(null);
        assertThat(form1).isNotEqualTo(form2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormDTO.class);
        FormDTO formDTO1 = new FormDTO();
        formDTO1.setId(1L);
        FormDTO formDTO2 = new FormDTO();
        assertThat(formDTO1).isNotEqualTo(formDTO2);
        formDTO2.setId(formDTO1.getId());
        assertThat(formDTO1).isEqualTo(formDTO2);
        formDTO2.setId(2L);
        assertThat(formDTO1).isNotEqualTo(formDTO2);
        formDTO1.setId(null);
        assertThat(formDTO1).isNotEqualTo(formDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(formMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(formMapper.fromId(null)).isNull();
    }
}
