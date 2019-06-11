package com.lxisoft.sas.service;

import com.lxisoft.sas.service.dto.DayOfWeekDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DayOfWeek.
 */
public interface DayOfWeekService {

    /**
     * Save a dayOfWeek.
     *
     * @param dayOfWeekDTO the entity to save
     * @return the persisted entity
     */
    DayOfWeekDTO save(DayOfWeekDTO dayOfWeekDTO);

    /**
     * Get all the dayOfWeeks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DayOfWeekDTO> findAll(Pageable pageable);


    /**
     * Get the "id" dayOfWeek.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DayOfWeekDTO> findOne(Long id);

    /**
     * Delete the "id" dayOfWeek.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
