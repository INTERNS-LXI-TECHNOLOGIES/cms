package com.lxisoft.sis.web.rest;

import com.lxisoft.sis.SmartInformationSystemApp;

import com.lxisoft.sis.domain.Event;
import com.lxisoft.sis.repository.EventRepository;
import com.lxisoft.sis.service.EventService;
import com.lxisoft.sis.service.dto.EventDTO;
import com.lxisoft.sis.service.mapper.EventMapper;
import com.lxisoft.sis.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import static com.lxisoft.sis.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lxisoft.sis.domain.enumeration.EventType;
/**
 * Test class for the EventResource REST controller.
 *
 * @see EventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartInformationSystemApp.class)
public class EventResourceIntTest {

    private static final EventType DEFAULT_EVENT_CATEGORY = EventType.ARTS;
    private static final EventType UPDATED_EVENT_CATEGORY = EventType.SPORTS;

    private static final String DEFAULT_EVENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_EVENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EVENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EVENT_VENUE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_VENUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private EventRepository eventRepository;

    @Mock
    private EventRepository eventRepositoryMock;

    @Autowired
    private EventMapper eventMapper;

    @Mock
    private EventService eventServiceMock;

    @Autowired
    private EventService eventService;

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

    private MockMvc restEventMockMvc;

    private Event event;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventResource eventResource = new EventResource(eventService);
        this.restEventMockMvc = MockMvcBuilders.standaloneSetup(eventResource)
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
    public static Event createEntity(EntityManager em) {
        Event event = new Event()
            .eventCategory(DEFAULT_EVENT_CATEGORY)
            .eventName(DEFAULT_EVENT_NAME)
            .eventDate(DEFAULT_EVENT_DATE)
            .eventVenue(DEFAULT_EVENT_VENUE)
            .active(DEFAULT_ACTIVE);
        return event;
    }

    @Before
    public void initTest() {
        event = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);
        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getEventCategory()).isEqualTo(DEFAULT_EVENT_CATEGORY);
        assertThat(testEvent.getEventName()).isEqualTo(DEFAULT_EVENT_NAME);
        assertThat(testEvent.getEventDate()).isEqualTo(DEFAULT_EVENT_DATE);
        assertThat(testEvent.getEventVenue()).isEqualTo(DEFAULT_EVENT_VENUE);
        assertThat(testEvent.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event with an existing ID
        event.setId(1L);
        EventDTO eventDTO = eventMapper.toDto(event);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEvents() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList
        restEventMockMvc.perform(get("/api/events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventCategory").value(hasItem(DEFAULT_EVENT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].eventName").value(hasItem(DEFAULT_EVENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].eventDate").value(hasItem(DEFAULT_EVENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].eventVenue").value(hasItem(DEFAULT_EVENT_VENUE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllEventsWithEagerRelationshipsIsEnabled() throws Exception {
        EventResource eventResource = new EventResource(eventServiceMock);
        when(eventServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restEventMockMvc = MockMvcBuilders.standaloneSetup(eventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEventMockMvc.perform(get("/api/events?eagerload=true"))
        .andExpect(status().isOk());

        verify(eventServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllEventsWithEagerRelationshipsIsNotEnabled() throws Exception {
        EventResource eventResource = new EventResource(eventServiceMock);
            when(eventServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restEventMockMvc = MockMvcBuilders.standaloneSetup(eventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEventMockMvc.perform(get("/api/events?eagerload=true"))
        .andExpect(status().isOk());

            verify(eventServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(event.getId().intValue()))
            .andExpect(jsonPath("$.eventCategory").value(DEFAULT_EVENT_CATEGORY.toString()))
            .andExpect(jsonPath("$.eventName").value(DEFAULT_EVENT_NAME.toString()))
            .andExpect(jsonPath("$.eventDate").value(DEFAULT_EVENT_DATE.toString()))
            .andExpect(jsonPath("$.eventVenue").value(DEFAULT_EVENT_VENUE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEvent() throws Exception {
        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event
        Event updatedEvent = eventRepository.findById(event.getId()).get();
        // Disconnect from session so that the updates on updatedEvent are not directly saved in db
        em.detach(updatedEvent);
        updatedEvent
            .eventCategory(UPDATED_EVENT_CATEGORY)
            .eventName(UPDATED_EVENT_NAME)
            .eventDate(UPDATED_EVENT_DATE)
            .eventVenue(UPDATED_EVENT_VENUE)
            .active(UPDATED_ACTIVE);
        EventDTO eventDTO = eventMapper.toDto(updatedEvent);

        restEventMockMvc.perform(put("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getEventCategory()).isEqualTo(UPDATED_EVENT_CATEGORY);
        assertThat(testEvent.getEventName()).isEqualTo(UPDATED_EVENT_NAME);
        assertThat(testEvent.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
        assertThat(testEvent.getEventVenue()).isEqualTo(UPDATED_EVENT_VENUE);
        assertThat(testEvent.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventMockMvc.perform(put("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeDelete = eventRepository.findAll().size();

        // Delete the event
        restEventMockMvc.perform(delete("/api/events/{id}", event.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Event.class);
        Event event1 = new Event();
        event1.setId(1L);
        Event event2 = new Event();
        event2.setId(event1.getId());
        assertThat(event1).isEqualTo(event2);
        event2.setId(2L);
        assertThat(event1).isNotEqualTo(event2);
        event1.setId(null);
        assertThat(event1).isNotEqualTo(event2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventDTO.class);
        EventDTO eventDTO1 = new EventDTO();
        eventDTO1.setId(1L);
        EventDTO eventDTO2 = new EventDTO();
        assertThat(eventDTO1).isNotEqualTo(eventDTO2);
        eventDTO2.setId(eventDTO1.getId());
        assertThat(eventDTO1).isEqualTo(eventDTO2);
        eventDTO2.setId(2L);
        assertThat(eventDTO1).isNotEqualTo(eventDTO2);
        eventDTO1.setId(null);
        assertThat(eventDTO1).isNotEqualTo(eventDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eventMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eventMapper.fromId(null)).isNull();
    }
}
