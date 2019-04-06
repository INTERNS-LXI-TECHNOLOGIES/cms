package com.lxisoft.sas.web.rest;

import com.lxisoft.sas.SmartAcademicSystemApp;

import com.lxisoft.sas.domain.UserDomain;
import com.lxisoft.sas.repository.UserDomainRepository;
import com.lxisoft.sas.service.UserDomainService;
import com.lxisoft.sas.service.dto.UserDomainDTO;
import com.lxisoft.sas.service.mapper.UserDomainMapper;
import com.lxisoft.sas.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.lxisoft.sas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lxisoft.sas.domain.enumeration.Department;
import com.lxisoft.sas.domain.enumeration.Semester;
/**
 * Test class for the UserDomainResource REST controller.
 *
 * @see UserDomainResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartAcademicSystemApp.class)
public class UserDomainResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Department DEFAULT_DEPARTMENT = Department.CSE;
    private static final Department UPDATED_DEPARTMENT = Department.ME;

    private static final Semester DEFAULT_SEMESTER = Semester.S1;
    private static final Semester UPDATED_SEMESTER = Semester.S2;

    private static final Long DEFAULT_CONTACT_NUMBER = 1L;
    private static final Long UPDATED_CONTACT_NUMBER = 2L;

    @Autowired
    private UserDomainRepository userDomainRepository;

    @Mock
    private UserDomainRepository userDomainRepositoryMock;

    @Autowired
    private UserDomainMapper userDomainMapper;

    @Mock
    private UserDomainService userDomainServiceMock;

    @Autowired
    private UserDomainService userDomainService;

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

    private MockMvc restUserDomainMockMvc;

    private UserDomain userDomain;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserDomainResource userDomainResource = new UserDomainResource(userDomainService);
        this.restUserDomainMockMvc = MockMvcBuilders.standaloneSetup(userDomainResource)
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
    public static UserDomain createEntity(EntityManager em) {
        UserDomain userDomain = new UserDomain()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .password(DEFAULT_PASSWORD)
            .department(DEFAULT_DEPARTMENT)
            .semester(DEFAULT_SEMESTER)
            .contactNumber(DEFAULT_CONTACT_NUMBER);
        return userDomain;
    }

    @Before
    public void initTest() {
        userDomain = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserDomain() throws Exception {
        int databaseSizeBeforeCreate = userDomainRepository.findAll().size();

        // Create the UserDomain
        UserDomainDTO userDomainDTO = userDomainMapper.toDto(userDomain);
        restUserDomainMockMvc.perform(post("/api/user-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDomainDTO)))
            .andExpect(status().isCreated());

        // Validate the UserDomain in the database
        List<UserDomain> userDomainList = userDomainRepository.findAll();
        assertThat(userDomainList).hasSize(databaseSizeBeforeCreate + 1);
        UserDomain testUserDomain = userDomainList.get(userDomainList.size() - 1);
        assertThat(testUserDomain.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testUserDomain.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUserDomain.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUserDomain.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUserDomain.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testUserDomain.getSemester()).isEqualTo(DEFAULT_SEMESTER);
        assertThat(testUserDomain.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void createUserDomainWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userDomainRepository.findAll().size();

        // Create the UserDomain with an existing ID
        userDomain.setId(1L);
        UserDomainDTO userDomainDTO = userDomainMapper.toDto(userDomain);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserDomainMockMvc.perform(post("/api/user-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDomainDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserDomain in the database
        List<UserDomain> userDomainList = userDomainRepository.findAll();
        assertThat(userDomainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserDomains() throws Exception {
        // Initialize the database
        userDomainRepository.saveAndFlush(userDomain);

        // Get all the userDomainList
        restUserDomainMockMvc.perform(get("/api/user-domains?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDomain.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT.toString())))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER.toString())))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllUserDomainsWithEagerRelationshipsIsEnabled() throws Exception {
        UserDomainResource userDomainResource = new UserDomainResource(userDomainServiceMock);
        when(userDomainServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restUserDomainMockMvc = MockMvcBuilders.standaloneSetup(userDomainResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserDomainMockMvc.perform(get("/api/user-domains?eagerload=true"))
        .andExpect(status().isOk());

        verify(userDomainServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUserDomainsWithEagerRelationshipsIsNotEnabled() throws Exception {
        UserDomainResource userDomainResource = new UserDomainResource(userDomainServiceMock);
            when(userDomainServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restUserDomainMockMvc = MockMvcBuilders.standaloneSetup(userDomainResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserDomainMockMvc.perform(get("/api/user-domains?eagerload=true"))
        .andExpect(status().isOk());

            verify(userDomainServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUserDomain() throws Exception {
        // Initialize the database
        userDomainRepository.saveAndFlush(userDomain);

        // Get the userDomain
        restUserDomainMockMvc.perform(get("/api/user-domains/{id}", userDomain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userDomain.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.semester").value(DEFAULT_SEMESTER.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserDomain() throws Exception {
        // Get the userDomain
        restUserDomainMockMvc.perform(get("/api/user-domains/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserDomain() throws Exception {
        // Initialize the database
        userDomainRepository.saveAndFlush(userDomain);

        int databaseSizeBeforeUpdate = userDomainRepository.findAll().size();

        // Update the userDomain
        UserDomain updatedUserDomain = userDomainRepository.findById(userDomain.getId()).get();
        // Disconnect from session so that the updates on updatedUserDomain are not directly saved in db
        em.detach(updatedUserDomain);
        updatedUserDomain
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .department(UPDATED_DEPARTMENT)
            .semester(UPDATED_SEMESTER)
            .contactNumber(UPDATED_CONTACT_NUMBER);
        UserDomainDTO userDomainDTO = userDomainMapper.toDto(updatedUserDomain);

        restUserDomainMockMvc.perform(put("/api/user-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDomainDTO)))
            .andExpect(status().isOk());

        // Validate the UserDomain in the database
        List<UserDomain> userDomainList = userDomainRepository.findAll();
        assertThat(userDomainList).hasSize(databaseSizeBeforeUpdate);
        UserDomain testUserDomain = userDomainList.get(userDomainList.size() - 1);
        assertThat(testUserDomain.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUserDomain.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUserDomain.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUserDomain.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUserDomain.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testUserDomain.getSemester()).isEqualTo(UPDATED_SEMESTER);
        assertThat(testUserDomain.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingUserDomain() throws Exception {
        int databaseSizeBeforeUpdate = userDomainRepository.findAll().size();

        // Create the UserDomain
        UserDomainDTO userDomainDTO = userDomainMapper.toDto(userDomain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserDomainMockMvc.perform(put("/api/user-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDomainDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserDomain in the database
        List<UserDomain> userDomainList = userDomainRepository.findAll();
        assertThat(userDomainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserDomain() throws Exception {
        // Initialize the database
        userDomainRepository.saveAndFlush(userDomain);

        int databaseSizeBeforeDelete = userDomainRepository.findAll().size();

        // Delete the userDomain
        restUserDomainMockMvc.perform(delete("/api/user-domains/{id}", userDomain.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserDomain> userDomainList = userDomainRepository.findAll();
        assertThat(userDomainList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDomain.class);
        UserDomain userDomain1 = new UserDomain();
        userDomain1.setId(1L);
        UserDomain userDomain2 = new UserDomain();
        userDomain2.setId(userDomain1.getId());
        assertThat(userDomain1).isEqualTo(userDomain2);
        userDomain2.setId(2L);
        assertThat(userDomain1).isNotEqualTo(userDomain2);
        userDomain1.setId(null);
        assertThat(userDomain1).isNotEqualTo(userDomain2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDomainDTO.class);
        UserDomainDTO userDomainDTO1 = new UserDomainDTO();
        userDomainDTO1.setId(1L);
        UserDomainDTO userDomainDTO2 = new UserDomainDTO();
        assertThat(userDomainDTO1).isNotEqualTo(userDomainDTO2);
        userDomainDTO2.setId(userDomainDTO1.getId());
        assertThat(userDomainDTO1).isEqualTo(userDomainDTO2);
        userDomainDTO2.setId(2L);
        assertThat(userDomainDTO1).isNotEqualTo(userDomainDTO2);
        userDomainDTO1.setId(null);
        assertThat(userDomainDTO1).isNotEqualTo(userDomainDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userDomainMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userDomainMapper.fromId(null)).isNull();
    }
}
