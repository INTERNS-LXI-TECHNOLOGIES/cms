package com.lxisoft.sis.web.rest;

import com.lxisoft.sis.SmartInformationSystemApp;

import com.lxisoft.sis.domain.StudyMaterial;
import com.lxisoft.sis.repository.StudyMaterialRepository;
import com.lxisoft.sis.service.StudyMaterialService;
import com.lxisoft.sis.service.dto.StudyMaterialDTO;
import com.lxisoft.sis.service.mapper.StudyMaterialMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.lxisoft.sis.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StudyMaterialResource REST controller.
 *
 * @see StudyMaterialResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartInformationSystemApp.class)
public class StudyMaterialResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MODULE = 1;
    private static final Integer UPDATED_MODULE = 2;

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private StudyMaterialRepository studyMaterialRepository;

    @Autowired
    private StudyMaterialMapper studyMaterialMapper;

    @Autowired
    private StudyMaterialService studyMaterialService;

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

    private MockMvc restStudyMaterialMockMvc;

    private StudyMaterial studyMaterial;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudyMaterialResource studyMaterialResource = new StudyMaterialResource(studyMaterialService);
        this.restStudyMaterialMockMvc = MockMvcBuilders.standaloneSetup(studyMaterialResource)
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
    public static StudyMaterial createEntity(EntityManager em) {
        StudyMaterial studyMaterial = new StudyMaterial()
            .title(DEFAULT_TITLE)
            .module(DEFAULT_MODULE)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        return studyMaterial;
    }

    @Before
    public void initTest() {
        studyMaterial = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudyMaterial() throws Exception {
        int databaseSizeBeforeCreate = studyMaterialRepository.findAll().size();

        // Create the StudyMaterial
        StudyMaterialDTO studyMaterialDTO = studyMaterialMapper.toDto(studyMaterial);
        restStudyMaterialMockMvc.perform(post("/api/study-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studyMaterialDTO)))
            .andExpect(status().isCreated());

        // Validate the StudyMaterial in the database
        List<StudyMaterial> studyMaterialList = studyMaterialRepository.findAll();
        assertThat(studyMaterialList).hasSize(databaseSizeBeforeCreate + 1);
        StudyMaterial testStudyMaterial = studyMaterialList.get(studyMaterialList.size() - 1);
        assertThat(testStudyMaterial.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testStudyMaterial.getModule()).isEqualTo(DEFAULT_MODULE);
        assertThat(testStudyMaterial.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testStudyMaterial.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createStudyMaterialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studyMaterialRepository.findAll().size();

        // Create the StudyMaterial with an existing ID
        studyMaterial.setId(1L);
        StudyMaterialDTO studyMaterialDTO = studyMaterialMapper.toDto(studyMaterial);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudyMaterialMockMvc.perform(post("/api/study-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studyMaterialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudyMaterial in the database
        List<StudyMaterial> studyMaterialList = studyMaterialRepository.findAll();
        assertThat(studyMaterialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStudyMaterials() throws Exception {
        // Initialize the database
        studyMaterialRepository.saveAndFlush(studyMaterial);

        // Get all the studyMaterialList
        restStudyMaterialMockMvc.perform(get("/api/study-materials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studyMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].module").value(hasItem(DEFAULT_MODULE)))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }
    
    @Test
    @Transactional
    public void getStudyMaterial() throws Exception {
        // Initialize the database
        studyMaterialRepository.saveAndFlush(studyMaterial);

        // Get the studyMaterial
        restStudyMaterialMockMvc.perform(get("/api/study-materials/{id}", studyMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studyMaterial.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.module").value(DEFAULT_MODULE))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    public void getNonExistingStudyMaterial() throws Exception {
        // Get the studyMaterial
        restStudyMaterialMockMvc.perform(get("/api/study-materials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudyMaterial() throws Exception {
        // Initialize the database
        studyMaterialRepository.saveAndFlush(studyMaterial);

        int databaseSizeBeforeUpdate = studyMaterialRepository.findAll().size();

        // Update the studyMaterial
        StudyMaterial updatedStudyMaterial = studyMaterialRepository.findById(studyMaterial.getId()).get();
        // Disconnect from session so that the updates on updatedStudyMaterial are not directly saved in db
        em.detach(updatedStudyMaterial);
        updatedStudyMaterial
            .title(UPDATED_TITLE)
            .module(UPDATED_MODULE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        StudyMaterialDTO studyMaterialDTO = studyMaterialMapper.toDto(updatedStudyMaterial);

        restStudyMaterialMockMvc.perform(put("/api/study-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studyMaterialDTO)))
            .andExpect(status().isOk());

        // Validate the StudyMaterial in the database
        List<StudyMaterial> studyMaterialList = studyMaterialRepository.findAll();
        assertThat(studyMaterialList).hasSize(databaseSizeBeforeUpdate);
        StudyMaterial testStudyMaterial = studyMaterialList.get(studyMaterialList.size() - 1);
        assertThat(testStudyMaterial.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testStudyMaterial.getModule()).isEqualTo(UPDATED_MODULE);
        assertThat(testStudyMaterial.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testStudyMaterial.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingStudyMaterial() throws Exception {
        int databaseSizeBeforeUpdate = studyMaterialRepository.findAll().size();

        // Create the StudyMaterial
        StudyMaterialDTO studyMaterialDTO = studyMaterialMapper.toDto(studyMaterial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudyMaterialMockMvc.perform(put("/api/study-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studyMaterialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudyMaterial in the database
        List<StudyMaterial> studyMaterialList = studyMaterialRepository.findAll();
        assertThat(studyMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudyMaterial() throws Exception {
        // Initialize the database
        studyMaterialRepository.saveAndFlush(studyMaterial);

        int databaseSizeBeforeDelete = studyMaterialRepository.findAll().size();

        // Delete the studyMaterial
        restStudyMaterialMockMvc.perform(delete("/api/study-materials/{id}", studyMaterial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudyMaterial> studyMaterialList = studyMaterialRepository.findAll();
        assertThat(studyMaterialList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudyMaterial.class);
        StudyMaterial studyMaterial1 = new StudyMaterial();
        studyMaterial1.setId(1L);
        StudyMaterial studyMaterial2 = new StudyMaterial();
        studyMaterial2.setId(studyMaterial1.getId());
        assertThat(studyMaterial1).isEqualTo(studyMaterial2);
        studyMaterial2.setId(2L);
        assertThat(studyMaterial1).isNotEqualTo(studyMaterial2);
        studyMaterial1.setId(null);
        assertThat(studyMaterial1).isNotEqualTo(studyMaterial2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudyMaterialDTO.class);
        StudyMaterialDTO studyMaterialDTO1 = new StudyMaterialDTO();
        studyMaterialDTO1.setId(1L);
        StudyMaterialDTO studyMaterialDTO2 = new StudyMaterialDTO();
        assertThat(studyMaterialDTO1).isNotEqualTo(studyMaterialDTO2);
        studyMaterialDTO2.setId(studyMaterialDTO1.getId());
        assertThat(studyMaterialDTO1).isEqualTo(studyMaterialDTO2);
        studyMaterialDTO2.setId(2L);
        assertThat(studyMaterialDTO1).isNotEqualTo(studyMaterialDTO2);
        studyMaterialDTO1.setId(null);
        assertThat(studyMaterialDTO1).isNotEqualTo(studyMaterialDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studyMaterialMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studyMaterialMapper.fromId(null)).isNull();
    }
}
