package com.lxisoft.sis.web.rest;

import com.lxisoft.sis.SmartInformationSystemApp;

import com.lxisoft.sis.domain.Qualification;
import com.lxisoft.sis.repository.QualificationRepository;
import com.lxisoft.sis.service.QualificationService;
import com.lxisoft.sis.service.dto.QualificationDTO;
import com.lxisoft.sis.service.mapper.QualificationMapper;
import com.lxisoft.sis.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.lxisoft.sis.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the QualificationResource REST controller.
 *
 * @see QualificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartInformationSystemApp.class)
public class QualificationResourceIntTest {

    private static final String DEFAULT_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_GRADE = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final String DEFAULT_UNIVERSITY = "AAAAAAAAAA";
    private static final String UPDATED_UNIVERSITY = "BBBBBBBBBB";

    private static final Double DEFAULT_MARKS = 1D;
    private static final Double UPDATED_MARKS = 2D;

    private static final Float DEFAULT_PERCENTAGE = 1F;
    private static final Float UPDATED_PERCENTAGE = 2F;

    @Autowired
    private QualificationRepository qualificationRepository;

    @Autowired
    private QualificationMapper qualificationMapper;

    @Autowired
    private QualificationService qualificationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restQualificationMockMvc;

    private Qualification qualification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QualificationResource qualificationResource = new QualificationResource(qualificationService);
        this.restQualificationMockMvc = MockMvcBuilders.standaloneSetup(qualificationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Qualification createEntity(EntityManager em) {
        Qualification qualification = new Qualification()
            .grade(DEFAULT_GRADE)
            .year(DEFAULT_YEAR)
            .university(DEFAULT_UNIVERSITY)
            .marks(DEFAULT_MARKS)
            .percentage(DEFAULT_PERCENTAGE);
        return qualification;
    }

    @Before
    public void initTest() {
        qualification = createEntity(em);
    }

    @Test
    @Transactional
    public void createQualification() throws Exception {
        int databaseSizeBeforeCreate = qualificationRepository.findAll().size();

        // Create the Qualification
        QualificationDTO qualificationDTO = qualificationMapper.toDto(qualification);
        restQualificationMockMvc.perform(post("/api/qualifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(qualificationDTO)))
            .andExpect(status().isCreated());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeCreate + 1);
        Qualification testQualification = qualificationList.get(qualificationList.size() - 1);
        assertThat(testQualification.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testQualification.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testQualification.getUniversity()).isEqualTo(DEFAULT_UNIVERSITY);
        assertThat(testQualification.getMarks()).isEqualTo(DEFAULT_MARKS);
        assertThat(testQualification.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void createQualificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = qualificationRepository.findAll().size();

        // Create the Qualification with an existing ID
        qualification.setId(1L);
        QualificationDTO qualificationDTO = qualificationMapper.toDto(qualification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQualificationMockMvc.perform(post("/api/qualifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(qualificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQualifications() throws Exception {
        // Initialize the database
        qualificationRepository.saveAndFlush(qualification);

        // Get all the qualificationList
        restQualificationMockMvc.perform(get("/api/qualifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qualification.getId().intValue())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].university").value(hasItem(DEFAULT_UNIVERSITY.toString())))
            .andExpect(jsonPath("$.[*].marks").value(hasItem(DEFAULT_MARKS.doubleValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getQualification() throws Exception {
        // Initialize the database
        qualificationRepository.saveAndFlush(qualification);

        // Get the qualification
        restQualificationMockMvc.perform(get("/api/qualifications/{id}", qualification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(qualification.getId().intValue()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.university").value(DEFAULT_UNIVERSITY.toString()))
            .andExpect(jsonPath("$.marks").value(DEFAULT_MARKS.doubleValue()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingQualification() throws Exception {
        // Get the qualification
        restQualificationMockMvc.perform(get("/api/qualifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQualification() throws Exception {
        // Initialize the database
        qualificationRepository.saveAndFlush(qualification);

        int databaseSizeBeforeUpdate = qualificationRepository.findAll().size();

        // Update the qualification
        Qualification updatedQualification = qualificationRepository.findById(qualification.getId()).get();
        // Disconnect from session so that the updates on updatedQualification are not directly saved in db
        em.detach(updatedQualification);
        updatedQualification
            .grade(UPDATED_GRADE)
            .year(UPDATED_YEAR)
            .university(UPDATED_UNIVERSITY)
            .marks(UPDATED_MARKS)
            .percentage(UPDATED_PERCENTAGE);
        QualificationDTO qualificationDTO = qualificationMapper.toDto(updatedQualification);

        restQualificationMockMvc.perform(put("/api/qualifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(qualificationDTO)))
            .andExpect(status().isOk());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeUpdate);
        Qualification testQualification = qualificationList.get(qualificationList.size() - 1);
        assertThat(testQualification.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testQualification.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testQualification.getUniversity()).isEqualTo(UPDATED_UNIVERSITY);
        assertThat(testQualification.getMarks()).isEqualTo(UPDATED_MARKS);
        assertThat(testQualification.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingQualification() throws Exception {
        int databaseSizeBeforeUpdate = qualificationRepository.findAll().size();

        // Create the Qualification
        QualificationDTO qualificationDTO = qualificationMapper.toDto(qualification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQualificationMockMvc.perform(put("/api/qualifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(qualificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQualification() throws Exception {
        // Initialize the database
        qualificationRepository.saveAndFlush(qualification);

        int databaseSizeBeforeDelete = qualificationRepository.findAll().size();

        // Delete the qualification
        restQualificationMockMvc.perform(delete("/api/qualifications/{id}", qualification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Qualification.class);
        Qualification qualification1 = new Qualification();
        qualification1.setId(1L);
        Qualification qualification2 = new Qualification();
        qualification2.setId(qualification1.getId());
        assertThat(qualification1).isEqualTo(qualification2);
        qualification2.setId(2L);
        assertThat(qualification1).isNotEqualTo(qualification2);
        qualification1.setId(null);
        assertThat(qualification1).isNotEqualTo(qualification2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QualificationDTO.class);
        QualificationDTO qualificationDTO1 = new QualificationDTO();
        qualificationDTO1.setId(1L);
        QualificationDTO qualificationDTO2 = new QualificationDTO();
        assertThat(qualificationDTO1).isNotEqualTo(qualificationDTO2);
        qualificationDTO2.setId(qualificationDTO1.getId());
        assertThat(qualificationDTO1).isEqualTo(qualificationDTO2);
        qualificationDTO2.setId(2L);
        assertThat(qualificationDTO1).isNotEqualTo(qualificationDTO2);
        qualificationDTO1.setId(null);
        assertThat(qualificationDTO1).isNotEqualTo(qualificationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(qualificationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(qualificationMapper.fromId(null)).isNull();
    }
}
