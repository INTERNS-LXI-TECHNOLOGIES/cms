package com.lxisoft.sas.service.impl;

import com.lxisoft.sas.service.ExamScheduleService;
import com.lxisoft.sas.domain.ExamSchedule;
import com.lxisoft.sas.repository.ExamScheduleRepository;
import com.lxisoft.sas.service.dto.ExamScheduleDTO;
import com.lxisoft.sas.service.mapper.ExamScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ExamSchedule.
 */
@Service
@Transactional
public class ExamScheduleServiceImpl implements ExamScheduleService {

    private final Logger log = LoggerFactory.getLogger(ExamScheduleServiceImpl.class);

    private final ExamScheduleRepository examScheduleRepository;

    private final ExamScheduleMapper examScheduleMapper;

    public ExamScheduleServiceImpl(ExamScheduleRepository examScheduleRepository, ExamScheduleMapper examScheduleMapper) {
        this.examScheduleRepository = examScheduleRepository;
        this.examScheduleMapper = examScheduleMapper;
    }

    /**
     * Save a examSchedule.
     *
     * @param examScheduleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExamScheduleDTO save(ExamScheduleDTO examScheduleDTO) {
        log.debug("Request to save ExamSchedule : {}", examScheduleDTO);
        ExamSchedule examSchedule = examScheduleMapper.toEntity(examScheduleDTO);
        examSchedule = examScheduleRepository.save(examSchedule);
        return examScheduleMapper.toDto(examSchedule);
    }

    /**
     * Get all the examSchedules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExamScheduleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExamSchedules");
        return examScheduleRepository.findAll(pageable)
            .map(examScheduleMapper::toDto);
    }


    /**
     * Get one examSchedule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ExamScheduleDTO> findOne(Long id) {
        log.debug("Request to get ExamSchedule : {}", id);
        return examScheduleRepository.findById(id)
            .map(examScheduleMapper::toDto);
    }

    /**
     * Delete the examSchedule by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamSchedule : {}", id);        examScheduleRepository.deleteById(id);
    }
}
