package com.lxisoft.sas.service;

import com.lxisoft.sas.service.dto.ExamHallDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ExamHall.
 */
public interface ExamHallService {

    /**
     * Save a examHall.
     *
     * @param examHallDTO the entity to save
     * @return the persisted entity
     */
    ExamHallDTO save(ExamHallDTO examHallDTO);

    /**
     * Get all the examHalls.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ExamHallDTO> findAll(Pageable pageable);


    /**
     * Get the "id" examHall.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ExamHallDTO> findOne(Long id);

    /**
     * Delete the "id" examHall.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
