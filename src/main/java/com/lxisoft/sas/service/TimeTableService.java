package com.lxisoft.sas.service;

import com.lxisoft.sas.service.dto.TimeTableDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TimeTable.
 */
public interface TimeTableService {

    /**
     * Save a timeTable.
     *
     * @param timeTableDTO the entity to save
     * @return the persisted entity
     */
    TimeTableDTO save(TimeTableDTO timeTableDTO);

    /**
     * Get all the timeTables.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TimeTableDTO> findAll(Pageable pageable);


    /**
     * Get the "id" timeTable.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TimeTableDTO> findOne(Long id);

    /**
     * Delete the "id" timeTable.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
