package com.lxisoft.sas.service.impl;

import com.lxisoft.sas.service.DayOfWeekService;
import com.lxisoft.sas.domain.DayOfWeek;
import com.lxisoft.sas.repository.DayOfWeekRepository;
import com.lxisoft.sas.service.dto.DayOfWeekDTO;
import com.lxisoft.sas.service.mapper.DayOfWeekMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DayOfWeek.
 */
@Service
@Transactional
public class DayOfWeekServiceImpl implements DayOfWeekService {

    private final Logger log = LoggerFactory.getLogger(DayOfWeekServiceImpl.class);

    private final DayOfWeekRepository dayOfWeekRepository;

    private final DayOfWeekMapper dayOfWeekMapper;

    public DayOfWeekServiceImpl(DayOfWeekRepository dayOfWeekRepository, DayOfWeekMapper dayOfWeekMapper) {
        this.dayOfWeekRepository = dayOfWeekRepository;
        this.dayOfWeekMapper = dayOfWeekMapper;
    }

    /**
     * Save a dayOfWeek.
     *
     * @param dayOfWeekDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DayOfWeekDTO save(DayOfWeekDTO dayOfWeekDTO) {
        log.debug("Request to save DayOfWeek : {}", dayOfWeekDTO);
        DayOfWeek dayOfWeek = dayOfWeekMapper.toEntity(dayOfWeekDTO);
        dayOfWeek = dayOfWeekRepository.save(dayOfWeek);
        return dayOfWeekMapper.toDto(dayOfWeek);
    }

    /**
     * Get all the dayOfWeeks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DayOfWeekDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DayOfWeeks");
        return dayOfWeekRepository.findAll(pageable)
            .map(dayOfWeekMapper::toDto);
    }


    /**
     * Get one dayOfWeek by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DayOfWeekDTO> findOne(Long id) {
        log.debug("Request to get DayOfWeek : {}", id);
        return dayOfWeekRepository.findById(id)
            .map(dayOfWeekMapper::toDto);
    }

    /**
     * Delete the dayOfWeek by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DayOfWeek : {}", id);        dayOfWeekRepository.deleteById(id);
    }
}
