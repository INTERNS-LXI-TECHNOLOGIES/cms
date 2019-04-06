package com.lxisoft.sas.service;

import com.lxisoft.sas.service.dto.ExamScheduleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ExamSchedule.
 */
public interface ExamScheduleService {

    /**
     * Save a examSchedule.
     *
     * @param examScheduleDTO the entity to save
     * @return the persisted entity
     */
    ExamScheduleDTO save(ExamScheduleDTO examScheduleDTO);

    /**
     * Get all the examSchedules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ExamScheduleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" examSchedule.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ExamScheduleDTO> findOne(Long id);

    /**
     * Delete the "id" examSchedule.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
