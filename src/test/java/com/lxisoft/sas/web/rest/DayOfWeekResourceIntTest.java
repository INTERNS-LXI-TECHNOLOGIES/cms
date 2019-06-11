package com.lxisoft.sas.web.rest;

import com.lxisoft.sas.SmartAcademicSystemApp;

import com.lxisoft.sas.domain.DayOfWeek;
import com.lxisoft.sas.repository.DayOfWeekRepository;
import com.lxisoft.sas.service.DayOfWeekService;
import com.lxisoft.sas.service.dto.DayOfWeekDTO;
import com.lxisoft.sas.service.mapper.DayOfWeekMapper;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.lxisoft.sas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DayOfWeekResource REST controller.
 *
 * @see DayOfWeekResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartAcademicSystemApp.class)
public class DayOfWeekResourceIntTest {

    private static final String DEFAULT_SUB_1 = "AAAAAAAAAA";
    private static final String UPDATED_SUB_1 = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_2 = "AAAAAAAAAA";
    private static final String UPDATED_SUB_2 = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_3 = "AAAAAAAAAA";
    private static final String UPDATED_SUB_3 = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_4 = "AAAAAAAAAA";
    private static final String UPDATED_SUB_4 = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_5 = "AAAAAAAAAA";
    private static final String UPDATED_SUB_5 = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_6 = "AAAAAAAAAA";
    private static final String UPDATED_SUB_6 = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_7 = "AAAAAAAAAA";
    private static final String UPDATED_SUB_7 = "BBBBBBBBBB";

    @Autowired
    private DayOfWeekRepository dayOfWeekRepository;

    @Autowired
    private DayOfWeekMapper dayOfWeekMapper;

    @Autowired
    private DayOfWeekService dayOfWeekService;

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

    private MockMvc restDayOfWeekMockMvc;

    private DayOfWeek dayOfWeek;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DayOfWeekResource dayOfWeekResource = new DayOfWeekResource(dayOfWeekService);
        this.restDayOfWeekMockMvc = MockMvcBuilders.standaloneSetup(dayOfWeekResource)
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
    public static DayOfWeek createEntity(EntityManager em) {
        DayOfWeek dayOfWeek = new DayOfWeek()
            .sub1(DEFAULT_SUB_1)
            .sub2(DEFAULT_SUB_2)
            .sub3(DEFAULT_SUB_3)
            .sub4(DEFAULT_SUB_4)
            .sub5(DEFAULT_SUB_5)
            .sub6(DEFAULT_SUB_6)
            .sub7(DEFAULT_SUB_7);
        return dayOfWeek;
    }

    @Before
    public void initTest() {
        dayOfWeek = createEntity(em);
    }

    @Test
    @Transactional
    public void createDayOfWeek() throws Exception {
        int databaseSizeBeforeCreate = dayOfWeekRepository.findAll().size();

        // Create the DayOfWeek
        DayOfWeekDTO dayOfWeekDTO = dayOfWeekMapper.toDto(dayOfWeek);
        restDayOfWeekMockMvc.perform(post("/api/day-of-weeks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayOfWeekDTO)))
            .andExpect(status().isCreated());

        // Validate the DayOfWeek in the database
        List<DayOfWeek> dayOfWeekList = dayOfWeekRepository.findAll();
        assertThat(dayOfWeekList).hasSize(databaseSizeBeforeCreate + 1);
        DayOfWeek testDayOfWeek = dayOfWeekList.get(dayOfWeekList.size() - 1);
        assertThat(testDayOfWeek.getSub1()).isEqualTo(DEFAULT_SUB_1);
        assertThat(testDayOfWeek.getSub2()).isEqualTo(DEFAULT_SUB_2);
        assertThat(testDayOfWeek.getSub3()).isEqualTo(DEFAULT_SUB_3);
        assertThat(testDayOfWeek.getSub4()).isEqualTo(DEFAULT_SUB_4);
        assertThat(testDayOfWeek.getSub5()).isEqualTo(DEFAULT_SUB_5);
        assertThat(testDayOfWeek.getSub6()).isEqualTo(DEFAULT_SUB_6);
        assertThat(testDayOfWeek.getSub7()).isEqualTo(DEFAULT_SUB_7);
    }

    @Test
    @Transactional
    public void createDayOfWeekWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dayOfWeekRepository.findAll().size();

        // Create the DayOfWeek with an existing ID
        dayOfWeek.setId(1L);
        DayOfWeekDTO dayOfWeekDTO = dayOfWeekMapper.toDto(dayOfWeek);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDayOfWeekMockMvc.perform(post("/api/day-of-weeks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayOfWeekDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DayOfWeek in the database
        List<DayOfWeek> dayOfWeekList = dayOfWeekRepository.findAll();
        assertThat(dayOfWeekList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDayOfWeeks() throws Exception {
        // Initialize the database
        dayOfWeekRepository.saveAndFlush(dayOfWeek);

        // Get all the dayOfWeekList
        restDayOfWeekMockMvc.perform(get("/api/day-of-weeks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dayOfWeek.getId().intValue())))
            .andExpect(jsonPath("$.[*].sub1").value(hasItem(DEFAULT_SUB_1.toString())))
            .andExpect(jsonPath("$.[*].sub2").value(hasItem(DEFAULT_SUB_2.toString())))
            .andExpect(jsonPath("$.[*].sub3").value(hasItem(DEFAULT_SUB_3.toString())))
            .andExpect(jsonPath("$.[*].sub4").value(hasItem(DEFAULT_SUB_4.toString())))
            .andExpect(jsonPath("$.[*].sub5").value(hasItem(DEFAULT_SUB_5.toString())))
            .andExpect(jsonPath("$.[*].sub6").value(hasItem(DEFAULT_SUB_6.toString())))
            .andExpect(jsonPath("$.[*].sub7").value(hasItem(DEFAULT_SUB_7.toString())));
    }
    
    @Test
    @Transactional
    public void getDayOfWeek() throws Exception {
        // Initialize the database
        dayOfWeekRepository.saveAndFlush(dayOfWeek);

        // Get the dayOfWeek
        restDayOfWeekMockMvc.perform(get("/api/day-of-weeks/{id}", dayOfWeek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dayOfWeek.getId().intValue()))
            .andExpect(jsonPath("$.sub1").value(DEFAULT_SUB_1.toString()))
            .andExpect(jsonPath("$.sub2").value(DEFAULT_SUB_2.toString()))
            .andExpect(jsonPath("$.sub3").value(DEFAULT_SUB_3.toString()))
            .andExpect(jsonPath("$.sub4").value(DEFAULT_SUB_4.toString()))
            .andExpect(jsonPath("$.sub5").value(DEFAULT_SUB_5.toString()))
            .andExpect(jsonPath("$.sub6").value(DEFAULT_SUB_6.toString()))
            .andExpect(jsonPath("$.sub7").value(DEFAULT_SUB_7.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDayOfWeek() throws Exception {
        // Get the dayOfWeek
        restDayOfWeekMockMvc.perform(get("/api/day-of-weeks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDayOfWeek() throws Exception {
        // Initialize the database
        dayOfWeekRepository.saveAndFlush(dayOfWeek);

        int databaseSizeBeforeUpdate = dayOfWeekRepository.findAll().size();

        // Update the dayOfWeek
        DayOfWeek updatedDayOfWeek = dayOfWeekRepository.findById(dayOfWeek.getId()).get();
        // Disconnect from session so that the updates on updatedDayOfWeek are not directly saved in db
        em.detach(updatedDayOfWeek);
        updatedDayOfWeek
            .sub1(UPDATED_SUB_1)
            .sub2(UPDATED_SUB_2)
            .sub3(UPDATED_SUB_3)
            .sub4(UPDATED_SUB_4)
            .sub5(UPDATED_SUB_5)
            .sub6(UPDATED_SUB_6)
            .sub7(UPDATED_SUB_7);
        DayOfWeekDTO dayOfWeekDTO = dayOfWeekMapper.toDto(updatedDayOfWeek);

        restDayOfWeekMockMvc.perform(put("/api/day-of-weeks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayOfWeekDTO)))
            .andExpect(status().isOk());

        // Validate the DayOfWeek in the database
        List<DayOfWeek> dayOfWeekList = dayOfWeekRepository.findAll();
        assertThat(dayOfWeekList).hasSize(databaseSizeBeforeUpdate);
        DayOfWeek testDayOfWeek = dayOfWeekList.get(dayOfWeekList.size() - 1);
        assertThat(testDayOfWeek.getSub1()).isEqualTo(UPDATED_SUB_1);
        assertThat(testDayOfWeek.getSub2()).isEqualTo(UPDATED_SUB_2);
        assertThat(testDayOfWeek.getSub3()).isEqualTo(UPDATED_SUB_3);
        assertThat(testDayOfWeek.getSub4()).isEqualTo(UPDATED_SUB_4);
        assertThat(testDayOfWeek.getSub5()).isEqualTo(UPDATED_SUB_5);
        assertThat(testDayOfWeek.getSub6()).isEqualTo(UPDATED_SUB_6);
        assertThat(testDayOfWeek.getSub7()).isEqualTo(UPDATED_SUB_7);
    }

    @Test
    @Transactional
    public void updateNonExistingDayOfWeek() throws Exception {
        int databaseSizeBeforeUpdate = dayOfWeekRepository.findAll().size();

        // Create the DayOfWeek
        DayOfWeekDTO dayOfWeekDTO = dayOfWeekMapper.toDto(dayOfWeek);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDayOfWeekMockMvc.perform(put("/api/day-of-weeks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayOfWeekDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DayOfWeek in the database
        List<DayOfWeek> dayOfWeekList = dayOfWeekRepository.findAll();
        assertThat(dayOfWeekList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDayOfWeek() throws Exception {
        // Initialize the database
        dayOfWeekRepository.saveAndFlush(dayOfWeek);

        int databaseSizeBeforeDelete = dayOfWeekRepository.findAll().size();

        // Delete the dayOfWeek
        restDayOfWeekMockMvc.perform(delete("/api/day-of-weeks/{id}", dayOfWeek.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DayOfWeek> dayOfWeekList = dayOfWeekRepository.findAll();
        assertThat(dayOfWeekList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DayOfWeek.class);
        DayOfWeek dayOfWeek1 = new DayOfWeek();
        dayOfWeek1.setId(1L);
        DayOfWeek dayOfWeek2 = new DayOfWeek();
        dayOfWeek2.setId(dayOfWeek1.getId());
        assertThat(dayOfWeek1).isEqualTo(dayOfWeek2);
        dayOfWeek2.setId(2L);
        assertThat(dayOfWeek1).isNotEqualTo(dayOfWeek2);
        dayOfWeek1.setId(null);
        assertThat(dayOfWeek1).isNotEqualTo(dayOfWeek2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DayOfWeekDTO.class);
        DayOfWeekDTO dayOfWeekDTO1 = new DayOfWeekDTO();
        dayOfWeekDTO1.setId(1L);
        DayOfWeekDTO dayOfWeekDTO2 = new DayOfWeekDTO();
        assertThat(dayOfWeekDTO1).isNotEqualTo(dayOfWeekDTO2);
        dayOfWeekDTO2.setId(dayOfWeekDTO1.getId());
        assertThat(dayOfWeekDTO1).isEqualTo(dayOfWeekDTO2);
        dayOfWeekDTO2.setId(2L);
        assertThat(dayOfWeekDTO1).isNotEqualTo(dayOfWeekDTO2);
        dayOfWeekDTO1.setId(null);
        assertThat(dayOfWeekDTO1).isNotEqualTo(dayOfWeekDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dayOfWeekMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dayOfWeekMapper.fromId(null)).isNull();
    }
}
