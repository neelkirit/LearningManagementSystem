package com.amogh.lms.web.rest;

import com.amogh.lms.AmoghServerApp;

import com.amogh.lms.domain.AadharEnrollForm;
import com.amogh.lms.repository.AadharEnrollFormRepository;
import com.amogh.lms.service.AadharEnrollFormService;
import com.amogh.lms.service.dto.AadharEnrollFormDTO;
import com.amogh.lms.service.mapper.AadharEnrollFormMapper;
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
 * Test class for the AadharEnrollFormResource REST controller.
 *
 * @see AadharEnrollFormResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmoghServerApp.class)
public class AadharEnrollFormResourceIntTest {

    private static final String DEFAULT_PRE_ENROLLMENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRE_ENROLLMENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TIN_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TIN_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_AGE = "AAAAAAAAAA";
    private static final String UPDATED_AGE = "BBBBBBBBBB";

    private static final String DEFAULT_DOB = "AAAAAAAAAA";
    private static final String UPDATED_DOB = "BBBBBBBBBB";

    private static final String DEFAULT_DECLARED = "AAAAAAAAAA";
    private static final String UPDATED_DECLARED = "BBBBBBBBBB";

    private static final String DEFAULT_VERIFIED = "AAAAAAAAAA";
    private static final String UPDATED_VERIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_CARE_OF = "AAAAAAAAAA";
    private static final String UPDATED_CARE_OF = "BBBBBBBBBB";

    private static final String DEFAULT_CARE_OF_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CARE_OF_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HOUSE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_HOUSE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_LANDMARK = "AAAAAAAAAA";
    private static final String UPDATED_LANDMARK = "BBBBBBBBBB";

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_VILLAGE = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE = "BBBBBBBBBB";

    private static final String DEFAULT_POST_OFFICE = "AAAAAAAAAA";
    private static final String UPDATED_POST_OFFICE = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_DISTRICT = "AAAAAAAAAA";
    private static final String UPDATED_SUB_DISTRICT = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MODILE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MODILE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PIN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PIN_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS_OF = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS_OF = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS_OF_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS_OF_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AADHAR_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_AADHAR_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_VERFICATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VERFICATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FOR_DOCUMENT_BASED = "AAAAAAAAAA";
    private static final String UPDATED_FOR_DOCUMENT_BASED = "BBBBBBBBBB";

    private static final String DEFAULT_FOR_INTRODUCER_BASED = "AAAAAAAAAA";
    private static final String UPDATED_FOR_INTRODUCER_BASED = "BBBBBBBBBB";

    private static final String DEFAULT_FOR_HO_F_BASED = "AAAAAAAAAA";
    private static final String UPDATED_FOR_HO_F_BASED = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    @Autowired
    private AadharEnrollFormRepository aadharEnrollFormRepository;

    @Autowired
    private AadharEnrollFormMapper aadharEnrollFormMapper;

    @Autowired
    private AadharEnrollFormService aadharEnrollFormService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAadharEnrollFormMockMvc;

    private AadharEnrollForm aadharEnrollForm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AadharEnrollFormResource aadharEnrollFormResource = new AadharEnrollFormResource(aadharEnrollFormService);
        this.restAadharEnrollFormMockMvc = MockMvcBuilders.standaloneSetup(aadharEnrollFormResource)
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
    public static AadharEnrollForm createEntity(EntityManager em) {
        AadharEnrollForm aadharEnrollForm = new AadharEnrollForm()
            .preEnrollmentID(DEFAULT_PRE_ENROLLMENT_ID)
            .tinNumber(DEFAULT_TIN_NUMBER)
            .fullName(DEFAULT_FULL_NAME)
            .gender(DEFAULT_GENDER)
            .age(DEFAULT_AGE)
            .dob(DEFAULT_DOB)
            .declared(DEFAULT_DECLARED)
            .verified(DEFAULT_VERIFIED)
            .careOf(DEFAULT_CARE_OF)
            .careOfName(DEFAULT_CARE_OF_NAME)
            .houseNumber(DEFAULT_HOUSE_NUMBER)
            .street(DEFAULT_STREET)
            .landmark(DEFAULT_LANDMARK)
            .area(DEFAULT_AREA)
            .village(DEFAULT_VILLAGE)
            .postOffice(DEFAULT_POST_OFFICE)
            .district(DEFAULT_DISTRICT)
            .subDistrict(DEFAULT_SUB_DISTRICT)
            .state(DEFAULT_STATE)
            .email(DEFAULT_EMAIL)
            .modileNumber(DEFAULT_MODILE_NUMBER)
            .pinCode(DEFAULT_PIN_CODE)
            .detailsOf(DEFAULT_DETAILS_OF)
            .detailsOfName(DEFAULT_DETAILS_OF_NAME)
            .aadharNumber(DEFAULT_AADHAR_NUMBER)
            .verficationType(DEFAULT_VERFICATION_TYPE)
            .forDocumentBased(DEFAULT_FOR_DOCUMENT_BASED)
            .forIntroducerBased(DEFAULT_FOR_INTRODUCER_BASED)
            .forHoFBased(DEFAULT_FOR_HO_F_BASED)
            .imageUrl(DEFAULT_IMAGE_URL);
        return aadharEnrollForm;
    }

    @Before
    public void initTest() {
        aadharEnrollForm = createEntity(em);
    }

    @Test
    @Transactional
    public void createAadharEnrollForm() throws Exception {
        int databaseSizeBeforeCreate = aadharEnrollFormRepository.findAll().size();

        // Create the AadharEnrollForm
        AadharEnrollFormDTO aadharEnrollFormDTO = aadharEnrollFormMapper.toDto(aadharEnrollForm);
        restAadharEnrollFormMockMvc.perform(post("/api/aadhar-enroll-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aadharEnrollFormDTO)))
            .andExpect(status().isCreated());

        // Validate the AadharEnrollForm in the database
        List<AadharEnrollForm> aadharEnrollFormList = aadharEnrollFormRepository.findAll();
        assertThat(aadharEnrollFormList).hasSize(databaseSizeBeforeCreate + 1);
        AadharEnrollForm testAadharEnrollForm = aadharEnrollFormList.get(aadharEnrollFormList.size() - 1);
        assertThat(testAadharEnrollForm.getPreEnrollmentID()).isEqualTo(DEFAULT_PRE_ENROLLMENT_ID);
        assertThat(testAadharEnrollForm.getTinNumber()).isEqualTo(DEFAULT_TIN_NUMBER);
        assertThat(testAadharEnrollForm.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testAadharEnrollForm.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testAadharEnrollForm.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testAadharEnrollForm.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testAadharEnrollForm.getDeclared()).isEqualTo(DEFAULT_DECLARED);
        assertThat(testAadharEnrollForm.getVerified()).isEqualTo(DEFAULT_VERIFIED);
        assertThat(testAadharEnrollForm.getCareOf()).isEqualTo(DEFAULT_CARE_OF);
        assertThat(testAadharEnrollForm.getCareOfName()).isEqualTo(DEFAULT_CARE_OF_NAME);
        assertThat(testAadharEnrollForm.getHouseNumber()).isEqualTo(DEFAULT_HOUSE_NUMBER);
        assertThat(testAadharEnrollForm.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testAadharEnrollForm.getLandmark()).isEqualTo(DEFAULT_LANDMARK);
        assertThat(testAadharEnrollForm.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testAadharEnrollForm.getVillage()).isEqualTo(DEFAULT_VILLAGE);
        assertThat(testAadharEnrollForm.getPostOffice()).isEqualTo(DEFAULT_POST_OFFICE);
        assertThat(testAadharEnrollForm.getDistrict()).isEqualTo(DEFAULT_DISTRICT);
        assertThat(testAadharEnrollForm.getSubDistrict()).isEqualTo(DEFAULT_SUB_DISTRICT);
        assertThat(testAadharEnrollForm.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testAadharEnrollForm.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAadharEnrollForm.getModileNumber()).isEqualTo(DEFAULT_MODILE_NUMBER);
        assertThat(testAadharEnrollForm.getPinCode()).isEqualTo(DEFAULT_PIN_CODE);
        assertThat(testAadharEnrollForm.getDetailsOf()).isEqualTo(DEFAULT_DETAILS_OF);
        assertThat(testAadharEnrollForm.getDetailsOfName()).isEqualTo(DEFAULT_DETAILS_OF_NAME);
        assertThat(testAadharEnrollForm.getAadharNumber()).isEqualTo(DEFAULT_AADHAR_NUMBER);
        assertThat(testAadharEnrollForm.getVerficationType()).isEqualTo(DEFAULT_VERFICATION_TYPE);
        assertThat(testAadharEnrollForm.getForDocumentBased()).isEqualTo(DEFAULT_FOR_DOCUMENT_BASED);
        assertThat(testAadharEnrollForm.getForIntroducerBased()).isEqualTo(DEFAULT_FOR_INTRODUCER_BASED);
        assertThat(testAadharEnrollForm.getForHoFBased()).isEqualTo(DEFAULT_FOR_HO_F_BASED);
        assertThat(testAadharEnrollForm.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void createAadharEnrollFormWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aadharEnrollFormRepository.findAll().size();

        // Create the AadharEnrollForm with an existing ID
        aadharEnrollForm.setId(1L);
        AadharEnrollFormDTO aadharEnrollFormDTO = aadharEnrollFormMapper.toDto(aadharEnrollForm);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAadharEnrollFormMockMvc.perform(post("/api/aadhar-enroll-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aadharEnrollFormDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AadharEnrollForm in the database
        List<AadharEnrollForm> aadharEnrollFormList = aadharEnrollFormRepository.findAll();
        assertThat(aadharEnrollFormList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAadharEnrollForms() throws Exception {
        // Initialize the database
        aadharEnrollFormRepository.saveAndFlush(aadharEnrollForm);

        // Get all the aadharEnrollFormList
        restAadharEnrollFormMockMvc.perform(get("/api/aadhar-enroll-forms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aadharEnrollForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].preEnrollmentID").value(hasItem(DEFAULT_PRE_ENROLLMENT_ID.toString())))
            .andExpect(jsonPath("$.[*].tinNumber").value(hasItem(DEFAULT_TIN_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].declared").value(hasItem(DEFAULT_DECLARED.toString())))
            .andExpect(jsonPath("$.[*].verified").value(hasItem(DEFAULT_VERIFIED.toString())))
            .andExpect(jsonPath("$.[*].careOf").value(hasItem(DEFAULT_CARE_OF.toString())))
            .andExpect(jsonPath("$.[*].careOfName").value(hasItem(DEFAULT_CARE_OF_NAME.toString())))
            .andExpect(jsonPath("$.[*].houseNumber").value(hasItem(DEFAULT_HOUSE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].landmark").value(hasItem(DEFAULT_LANDMARK.toString())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.toString())))
            .andExpect(jsonPath("$.[*].village").value(hasItem(DEFAULT_VILLAGE.toString())))
            .andExpect(jsonPath("$.[*].postOffice").value(hasItem(DEFAULT_POST_OFFICE.toString())))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT.toString())))
            .andExpect(jsonPath("$.[*].subDistrict").value(hasItem(DEFAULT_SUB_DISTRICT.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].modileNumber").value(hasItem(DEFAULT_MODILE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE.toString())))
            .andExpect(jsonPath("$.[*].detailsOf").value(hasItem(DEFAULT_DETAILS_OF.toString())))
            .andExpect(jsonPath("$.[*].detailsOfName").value(hasItem(DEFAULT_DETAILS_OF_NAME.toString())))
            .andExpect(jsonPath("$.[*].aadharNumber").value(hasItem(DEFAULT_AADHAR_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].verficationType").value(hasItem(DEFAULT_VERFICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].forDocumentBased").value(hasItem(DEFAULT_FOR_DOCUMENT_BASED.toString())))
            .andExpect(jsonPath("$.[*].forIntroducerBased").value(hasItem(DEFAULT_FOR_INTRODUCER_BASED.toString())))
            .andExpect(jsonPath("$.[*].forHoFBased").value(hasItem(DEFAULT_FOR_HO_F_BASED.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())));
    }

    @Test
    @Transactional
    public void getAadharEnrollForm() throws Exception {
        // Initialize the database
        aadharEnrollFormRepository.saveAndFlush(aadharEnrollForm);

        // Get the aadharEnrollForm
        restAadharEnrollFormMockMvc.perform(get("/api/aadhar-enroll-forms/{id}", aadharEnrollForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aadharEnrollForm.getId().intValue()))
            .andExpect(jsonPath("$.preEnrollmentID").value(DEFAULT_PRE_ENROLLMENT_ID.toString()))
            .andExpect(jsonPath("$.tinNumber").value(DEFAULT_TIN_NUMBER.toString()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.declared").value(DEFAULT_DECLARED.toString()))
            .andExpect(jsonPath("$.verified").value(DEFAULT_VERIFIED.toString()))
            .andExpect(jsonPath("$.careOf").value(DEFAULT_CARE_OF.toString()))
            .andExpect(jsonPath("$.careOfName").value(DEFAULT_CARE_OF_NAME.toString()))
            .andExpect(jsonPath("$.houseNumber").value(DEFAULT_HOUSE_NUMBER.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.landmark").value(DEFAULT_LANDMARK.toString()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.toString()))
            .andExpect(jsonPath("$.village").value(DEFAULT_VILLAGE.toString()))
            .andExpect(jsonPath("$.postOffice").value(DEFAULT_POST_OFFICE.toString()))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT.toString()))
            .andExpect(jsonPath("$.subDistrict").value(DEFAULT_SUB_DISTRICT.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.modileNumber").value(DEFAULT_MODILE_NUMBER.toString()))
            .andExpect(jsonPath("$.pinCode").value(DEFAULT_PIN_CODE.toString()))
            .andExpect(jsonPath("$.detailsOf").value(DEFAULT_DETAILS_OF.toString()))
            .andExpect(jsonPath("$.detailsOfName").value(DEFAULT_DETAILS_OF_NAME.toString()))
            .andExpect(jsonPath("$.aadharNumber").value(DEFAULT_AADHAR_NUMBER.toString()))
            .andExpect(jsonPath("$.verficationType").value(DEFAULT_VERFICATION_TYPE.toString()))
            .andExpect(jsonPath("$.forDocumentBased").value(DEFAULT_FOR_DOCUMENT_BASED.toString()))
            .andExpect(jsonPath("$.forIntroducerBased").value(DEFAULT_FOR_INTRODUCER_BASED.toString()))
            .andExpect(jsonPath("$.forHoFBased").value(DEFAULT_FOR_HO_F_BASED.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAadharEnrollForm() throws Exception {
        // Get the aadharEnrollForm
        restAadharEnrollFormMockMvc.perform(get("/api/aadhar-enroll-forms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAadharEnrollForm() throws Exception {
        // Initialize the database
        aadharEnrollFormRepository.saveAndFlush(aadharEnrollForm);
        int databaseSizeBeforeUpdate = aadharEnrollFormRepository.findAll().size();

        // Update the aadharEnrollForm
        AadharEnrollForm updatedAadharEnrollForm = aadharEnrollFormRepository.findOne(aadharEnrollForm.getId());
        // Disconnect from session so that the updates on updatedAadharEnrollForm are not directly saved in db
        em.detach(updatedAadharEnrollForm);
        updatedAadharEnrollForm
            .preEnrollmentID(UPDATED_PRE_ENROLLMENT_ID)
            .tinNumber(UPDATED_TIN_NUMBER)
            .fullName(UPDATED_FULL_NAME)
            .gender(UPDATED_GENDER)
            .age(UPDATED_AGE)
            .dob(UPDATED_DOB)
            .declared(UPDATED_DECLARED)
            .verified(UPDATED_VERIFIED)
            .careOf(UPDATED_CARE_OF)
            .careOfName(UPDATED_CARE_OF_NAME)
            .houseNumber(UPDATED_HOUSE_NUMBER)
            .street(UPDATED_STREET)
            .landmark(UPDATED_LANDMARK)
            .area(UPDATED_AREA)
            .village(UPDATED_VILLAGE)
            .postOffice(UPDATED_POST_OFFICE)
            .district(UPDATED_DISTRICT)
            .subDistrict(UPDATED_SUB_DISTRICT)
            .state(UPDATED_STATE)
            .email(UPDATED_EMAIL)
            .modileNumber(UPDATED_MODILE_NUMBER)
            .pinCode(UPDATED_PIN_CODE)
            .detailsOf(UPDATED_DETAILS_OF)
            .detailsOfName(UPDATED_DETAILS_OF_NAME)
            .aadharNumber(UPDATED_AADHAR_NUMBER)
            .verficationType(UPDATED_VERFICATION_TYPE)
            .forDocumentBased(UPDATED_FOR_DOCUMENT_BASED)
            .forIntroducerBased(UPDATED_FOR_INTRODUCER_BASED)
            .forHoFBased(UPDATED_FOR_HO_F_BASED)
            .imageUrl(UPDATED_IMAGE_URL);
        AadharEnrollFormDTO aadharEnrollFormDTO = aadharEnrollFormMapper.toDto(updatedAadharEnrollForm);

        restAadharEnrollFormMockMvc.perform(put("/api/aadhar-enroll-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aadharEnrollFormDTO)))
            .andExpect(status().isOk());

        // Validate the AadharEnrollForm in the database
        List<AadharEnrollForm> aadharEnrollFormList = aadharEnrollFormRepository.findAll();
        assertThat(aadharEnrollFormList).hasSize(databaseSizeBeforeUpdate);
        AadharEnrollForm testAadharEnrollForm = aadharEnrollFormList.get(aadharEnrollFormList.size() - 1);
        assertThat(testAadharEnrollForm.getPreEnrollmentID()).isEqualTo(UPDATED_PRE_ENROLLMENT_ID);
        assertThat(testAadharEnrollForm.getTinNumber()).isEqualTo(UPDATED_TIN_NUMBER);
        assertThat(testAadharEnrollForm.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testAadharEnrollForm.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testAadharEnrollForm.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testAadharEnrollForm.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testAadharEnrollForm.getDeclared()).isEqualTo(UPDATED_DECLARED);
        assertThat(testAadharEnrollForm.getVerified()).isEqualTo(UPDATED_VERIFIED);
        assertThat(testAadharEnrollForm.getCareOf()).isEqualTo(UPDATED_CARE_OF);
        assertThat(testAadharEnrollForm.getCareOfName()).isEqualTo(UPDATED_CARE_OF_NAME);
        assertThat(testAadharEnrollForm.getHouseNumber()).isEqualTo(UPDATED_HOUSE_NUMBER);
        assertThat(testAadharEnrollForm.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testAadharEnrollForm.getLandmark()).isEqualTo(UPDATED_LANDMARK);
        assertThat(testAadharEnrollForm.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testAadharEnrollForm.getVillage()).isEqualTo(UPDATED_VILLAGE);
        assertThat(testAadharEnrollForm.getPostOffice()).isEqualTo(UPDATED_POST_OFFICE);
        assertThat(testAadharEnrollForm.getDistrict()).isEqualTo(UPDATED_DISTRICT);
        assertThat(testAadharEnrollForm.getSubDistrict()).isEqualTo(UPDATED_SUB_DISTRICT);
        assertThat(testAadharEnrollForm.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAadharEnrollForm.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAadharEnrollForm.getModileNumber()).isEqualTo(UPDATED_MODILE_NUMBER);
        assertThat(testAadharEnrollForm.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
        assertThat(testAadharEnrollForm.getDetailsOf()).isEqualTo(UPDATED_DETAILS_OF);
        assertThat(testAadharEnrollForm.getDetailsOfName()).isEqualTo(UPDATED_DETAILS_OF_NAME);
        assertThat(testAadharEnrollForm.getAadharNumber()).isEqualTo(UPDATED_AADHAR_NUMBER);
        assertThat(testAadharEnrollForm.getVerficationType()).isEqualTo(UPDATED_VERFICATION_TYPE);
        assertThat(testAadharEnrollForm.getForDocumentBased()).isEqualTo(UPDATED_FOR_DOCUMENT_BASED);
        assertThat(testAadharEnrollForm.getForIntroducerBased()).isEqualTo(UPDATED_FOR_INTRODUCER_BASED);
        assertThat(testAadharEnrollForm.getForHoFBased()).isEqualTo(UPDATED_FOR_HO_F_BASED);
        assertThat(testAadharEnrollForm.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingAadharEnrollForm() throws Exception {
        int databaseSizeBeforeUpdate = aadharEnrollFormRepository.findAll().size();

        // Create the AadharEnrollForm
        AadharEnrollFormDTO aadharEnrollFormDTO = aadharEnrollFormMapper.toDto(aadharEnrollForm);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAadharEnrollFormMockMvc.perform(put("/api/aadhar-enroll-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aadharEnrollFormDTO)))
            .andExpect(status().isCreated());

        // Validate the AadharEnrollForm in the database
        List<AadharEnrollForm> aadharEnrollFormList = aadharEnrollFormRepository.findAll();
        assertThat(aadharEnrollFormList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAadharEnrollForm() throws Exception {
        // Initialize the database
        aadharEnrollFormRepository.saveAndFlush(aadharEnrollForm);
        int databaseSizeBeforeDelete = aadharEnrollFormRepository.findAll().size();

        // Get the aadharEnrollForm
        restAadharEnrollFormMockMvc.perform(delete("/api/aadhar-enroll-forms/{id}", aadharEnrollForm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AadharEnrollForm> aadharEnrollFormList = aadharEnrollFormRepository.findAll();
        assertThat(aadharEnrollFormList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AadharEnrollForm.class);
        AadharEnrollForm aadharEnrollForm1 = new AadharEnrollForm();
        aadharEnrollForm1.setId(1L);
        AadharEnrollForm aadharEnrollForm2 = new AadharEnrollForm();
        aadharEnrollForm2.setId(aadharEnrollForm1.getId());
        assertThat(aadharEnrollForm1).isEqualTo(aadharEnrollForm2);
        aadharEnrollForm2.setId(2L);
        assertThat(aadharEnrollForm1).isNotEqualTo(aadharEnrollForm2);
        aadharEnrollForm1.setId(null);
        assertThat(aadharEnrollForm1).isNotEqualTo(aadharEnrollForm2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AadharEnrollFormDTO.class);
        AadharEnrollFormDTO aadharEnrollFormDTO1 = new AadharEnrollFormDTO();
        aadharEnrollFormDTO1.setId(1L);
        AadharEnrollFormDTO aadharEnrollFormDTO2 = new AadharEnrollFormDTO();
        assertThat(aadharEnrollFormDTO1).isNotEqualTo(aadharEnrollFormDTO2);
        aadharEnrollFormDTO2.setId(aadharEnrollFormDTO1.getId());
        assertThat(aadharEnrollFormDTO1).isEqualTo(aadharEnrollFormDTO2);
        aadharEnrollFormDTO2.setId(2L);
        assertThat(aadharEnrollFormDTO1).isNotEqualTo(aadharEnrollFormDTO2);
        aadharEnrollFormDTO1.setId(null);
        assertThat(aadharEnrollFormDTO1).isNotEqualTo(aadharEnrollFormDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(aadharEnrollFormMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(aadharEnrollFormMapper.fromId(null)).isNull();
    }
}
