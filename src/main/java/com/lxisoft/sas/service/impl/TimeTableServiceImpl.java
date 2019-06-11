package com.lxisoft.sas.service.impl;

import com.lxisoft.sas.service.TimeTableService;
import com.lxisoft.sas.domain.TimeTable;
import com.lxisoft.sas.repository.TimeTableRepository;
import com.lxisoft.sas.service.dto.TimeTableDTO;
import com.lxisoft.sas.service.mapper.TimeTableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TimeTable.
 */
@Service
@Transactional
public class TimeTableServiceImpl implements TimeTableService {

    private final Logger log = LoggerFactory.getLogger(TimeTableServiceImpl.class);

    private final TimeTableRepository timeTableRepository;

    private final TimeTableMapper timeTableMapper;

    public TimeTableServiceImpl(TimeTableRepository timeTableRepository, TimeTableMapper timeTableMapper) {
        this.timeTableRepository = timeTableRepository;
        this.timeTableMapper = timeTableMapper;
    }

    /**
     * Save a timeTable.
     *
     * @param timeTableDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TimeTableDTO save(TimeTableDTO timeTableDTO) {
        log.debug("Request to save TimeTable : {}", timeTableDTO);
        TimeTable timeTable = timeTableMapper.toEntity(timeTableDTO);
        timeTable = timeTableRepository.save(timeTable);
        return timeTableMapper.toDto(timeTable);
    }

    /**
     * Get all the timeTables.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TimeTableDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TimeTables");
        return timeTableRepository.findAll(pageable)
            .map(timeTableMapper::toDto);
    }


    /**
     * Get one timeTable by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TimeTableDTO> findOne(Long id) {
        log.debug("Request to get TimeTable : {}", id);
        return timeTableRepository.findById(id)
            .map(timeTableMapper::toDto);
    }

    /**
     * Delete the timeTable by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TimeTable : {}", id);        timeTableRepository.deleteById(id);
    }
}
