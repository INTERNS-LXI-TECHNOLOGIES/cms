package com.lxisoft.sis.service.impl;

import com.lxisoft.sis.service.ExamHallService;
import com.lxisoft.sis.domain.ExamHall;
import com.lxisoft.sis.repository.ExamHallRepository;
import com.lxisoft.sis.service.dto.ExamHallDTO;
import com.lxisoft.sis.service.mapper.ExamHallMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ExamHall.
 */
@Service
@Transactional
public class ExamHallServiceImpl implements ExamHallService {

    private final Logger log = LoggerFactory.getLogger(ExamHallServiceImpl.class);

    private final ExamHallRepository examHallRepository;

    private final ExamHallMapper examHallMapper;

    public ExamHallServiceImpl(ExamHallRepository examHallRepository, ExamHallMapper examHallMapper) {
        this.examHallRepository = examHallRepository;
        this.examHallMapper = examHallMapper;
    }

    /**
     * Save a examHall.
     *
     * @param examHallDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExamHallDTO save(ExamHallDTO examHallDTO) {
        log.debug("Request to save ExamHall : {}", examHallDTO);
        ExamHall examHall = examHallMapper.toEntity(examHallDTO);
        examHall = examHallRepository.save(examHall);
        return examHallMapper.toDto(examHall);
    }

    /**
     * Get all the examHalls.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExamHallDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExamHalls");
        return examHallRepository.findAll(pageable)
            .map(examHallMapper::toDto);
    }


    /**
     * Get one examHall by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ExamHallDTO> findOne(Long id) {
        log.debug("Request to get ExamHall : {}", id);
        return examHallRepository.findById(id)
            .map(examHallMapper::toDto);
    }

    /**
     * Delete the examHall by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamHall : {}", id);        examHallRepository.deleteById(id);
    }
}
