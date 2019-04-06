package com.lxisoft.sis.service.impl;

import com.lxisoft.sis.service.EventService;
import com.lxisoft.sis.domain.Event;
import com.lxisoft.sis.repository.EventRepository;
import com.lxisoft.sis.service.dto.EventDTO;
import com.lxisoft.sis.service.mapper.EventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Event.
 */
@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    /**
     * Save a event.
     *
     * @param eventDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EventDTO save(EventDTO eventDTO) {
        log.debug("Request to save Event : {}", eventDTO);
        Event event = eventMapper.toEntity(eventDTO);
        event = eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    /**
     * Get all the events.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Events");
        return eventRepository.findAll(pageable)
            .map(eventMapper::toDto);
    }

    /**
     * Get all the Event with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<EventDTO> findAllWithEagerRelationships(Pageable pageable) {
        return eventRepository.findAllWithEagerRelationships(pageable).map(eventMapper::toDto);
    }
    

    /**
     * Get one event by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EventDTO> findOne(Long id) {
        log.debug("Request to get Event : {}", id);
        return eventRepository.findOneWithEagerRelationships(id)
            .map(eventMapper::toDto);
    }

    /**
     * Delete the event by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Event : {}", id);        eventRepository.deleteById(id);
    }
}
