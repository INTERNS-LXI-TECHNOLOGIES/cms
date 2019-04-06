package com.lxisoft.sas.web.rest;

import com.lxisoft.sas.SmartAcademicSystemApp;

import com.lxisoft.sas.domain.Attatchment;
import com.lxisoft.sas.repository.AttatchmentRepository;
import com.lxisoft.sas.service.AttatchmentService;
import com.lxisoft.sas.service.dto.AttatchmentDTO;
import com.lxisoft.sas.service.mapper.AttatchmentMapper;
import com.lxisoft.sas.web.rest.errors.ExceptionTranslator;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.lxisoft.sas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AttatchmentResource REST controller.
 *
 * @see AttatchmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartAcademicSystemApp.class)
public class AttatchmentResourceIntTest {

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private AttatchmentRepository attatchmentRepository;

    @Autowired
    private AttatchmentMapper attatchmentMapper;

    @Autowired
    private AttatchmentService attatchmentService;

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

    private MockMvc restAttatchmentMockMvc;

    private Attatchment attatchment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AttatchmentResource attatchmentResource = new AttatchmentResource(attatchmentService);
        this.restAttatchmentMockMvc = MockMvcBuilders.standaloneSetup(attatchmentResource)
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
    public static Attatchment createEntity(EntityManager em) {
        Attatchment attatchment = new Attatchment()
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        return attatchment;
    }

    @Before
    public void initTest() {
        attatchment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttatchment() throws Exception {
        int databaseSizeBeforeCreate = attatchmentRepository.findAll().size();

        // Create the Attatchment
        AttatchmentDTO attatchmentDTO = attatchmentMapper.toDto(attatchment);
        restAttatchmentMockMvc.perform(post("/api/attatchments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attatchmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Attatchment in the database
        List<Attatchment> attatchmentList = attatchmentRepository.findAll();
        assertThat(attatchmentList).hasSize(databaseSizeBeforeCreate + 1);
        Attatchment testAttatchment = attatchmentList.get(attatchmentList.size() - 1);
        assertThat(testAttatchment.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testAttatchment.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createAttatchmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attatchmentRepository.findAll().size();

        // Create the Attatchment with an existing ID
        attatchment.setId(1L);
        AttatchmentDTO attatchmentDTO = attatchmentMapper.toDto(attatchment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttatchmentMockMvc.perform(post("/api/attatchments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attatchmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attatchment in the database
        List<Attatchment> attatchmentList = attatchmentRepository.findAll();
        assertThat(attatchmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAttatchments() throws Exception {
        // Initialize the database
        attatchmentRepository.saveAndFlush(attatchment);

        // Get all the attatchmentList
        restAttatchmentMockMvc.perform(get("/api/attatchments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attatchment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }
    
    @Test
    @Transactional
    public void getAttatchment() throws Exception {
        // Initialize the database
        attatchmentRepository.saveAndFlush(attatchment);

        // Get the attatchment
        restAttatchmentMockMvc.perform(get("/api/attatchments/{id}", attatchment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(attatchment.getId().intValue()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    public void getNonExistingAttatchment() throws Exception {
        // Get the attatchment
        restAttatchmentMockMvc.perform(get("/api/attatchments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttatchment() throws Exception {
        // Initialize the database
        attatchmentRepository.saveAndFlush(attatchment);

        int databaseSizeBeforeUpdate = attatchmentRepository.findAll().size();

        // Update the attatchment
        Attatchment updatedAttatchment = attatchmentRepository.findById(attatchment.getId()).get();
        // Disconnect from session so that the updates on updatedAttatchment are not directly saved in db
        em.detach(updatedAttatchment);
        updatedAttatchment
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        AttatchmentDTO attatchmentDTO = attatchmentMapper.toDto(updatedAttatchment);

        restAttatchmentMockMvc.perform(put("/api/attatchments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attatchmentDTO)))
            .andExpect(status().isOk());

        // Validate the Attatchment in the database
        List<Attatchment> attatchmentList = attatchmentRepository.findAll();
        assertThat(attatchmentList).hasSize(databaseSizeBeforeUpdate);
        Attatchment testAttatchment = attatchmentList.get(attatchmentList.size() - 1);
        assertThat(testAttatchment.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testAttatchment.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAttatchment() throws Exception {
        int databaseSizeBeforeUpdate = attatchmentRepository.findAll().size();

        // Create the Attatchment
        AttatchmentDTO attatchmentDTO = attatchmentMapper.toDto(attatchment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttatchmentMockMvc.perform(put("/api/attatchments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attatchmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attatchment in the database
        List<Attatchment> attatchmentList = attatchmentRepository.findAll();
        assertThat(attatchmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttatchment() throws Exception {
        // Initialize the database
        attatchmentRepository.saveAndFlush(attatchment);

        int databaseSizeBeforeDelete = attatchmentRepository.findAll().size();

        // Delete the attatchment
        restAttatchmentMockMvc.perform(delete("/api/attatchments/{id}", attatchment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Attatchment> attatchmentList = attatchmentRepository.findAll();
        assertThat(attatchmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attatchment.class);
        Attatchment attatchment1 = new Attatchment();
        attatchment1.setId(1L);
        Attatchment attatchment2 = new Attatchment();
        attatchment2.setId(attatchment1.getId());
        assertThat(attatchment1).isEqualTo(attatchment2);
        attatchment2.setId(2L);
        assertThat(attatchment1).isNotEqualTo(attatchment2);
        attatchment1.setId(null);
        assertThat(attatchment1).isNotEqualTo(attatchment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttatchmentDTO.class);
        AttatchmentDTO attatchmentDTO1 = new AttatchmentDTO();
        attatchmentDTO1.setId(1L);
        AttatchmentDTO attatchmentDTO2 = new AttatchmentDTO();
        assertThat(attatchmentDTO1).isNotEqualTo(attatchmentDTO2);
        attatchmentDTO2.setId(attatchmentDTO1.getId());
        assertThat(attatchmentDTO1).isEqualTo(attatchmentDTO2);
        attatchmentDTO2.setId(2L);
        assertThat(attatchmentDTO1).isNotEqualTo(attatchmentDTO2);
        attatchmentDTO1.setId(null);
        assertThat(attatchmentDTO1).isNotEqualTo(attatchmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(attatchmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(attatchmentMapper.fromId(null)).isNull();
    }
}
