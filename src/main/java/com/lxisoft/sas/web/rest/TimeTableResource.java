package com.lxisoft.sas.web.rest;
import com.lxisoft.sas.service.TimeTableService;
import com.lxisoft.sas.web.rest.errors.BadRequestAlertException;
import com.lxisoft.sas.web.rest.util.HeaderUtil;
import com.lxisoft.sas.web.rest.util.PaginationUtil;
import com.lxisoft.sas.service.dto.TimeTableDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TimeTable.
 */
@RestController
@RequestMapping("/api")
public class TimeTableResource {

    private final Logger log = LoggerFactory.getLogger(TimeTableResource.class);

    private static final String ENTITY_NAME = "timeTable";

    private final TimeTableService timeTableService;

    public TimeTableResource(TimeTableService timeTableService) {
        this.timeTableService = timeTableService;
    }

    /**
     * POST  /time-tables : Create a new timeTable.
     *
     * @param timeTableDTO the timeTableDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timeTableDTO, or with status 400 (Bad Request) if the timeTable has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/time-tables")
    public ResponseEntity<TimeTableDTO> createTimeTable(@RequestBody TimeTableDTO timeTableDTO) throws URISyntaxException {
        log.debug("REST request to save TimeTable : {}", timeTableDTO);
        if (timeTableDTO.getId() != null) {
            throw new BadRequestAlertException("A new timeTable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimeTableDTO result = timeTableService.save(timeTableDTO);
        return ResponseEntity.created(new URI("/api/time-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /time-tables : Updates an existing timeTable.
     *
     * @param timeTableDTO the timeTableDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timeTableDTO,
     * or with status 400 (Bad Request) if the timeTableDTO is not valid,
     * or with status 500 (Internal Server Error) if the timeTableDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/time-tables")
    public ResponseEntity<TimeTableDTO> updateTimeTable(@RequestBody TimeTableDTO timeTableDTO) throws URISyntaxException {
        log.debug("REST request to update TimeTable : {}", timeTableDTO);
        if (timeTableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TimeTableDTO result = timeTableService.save(timeTableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timeTableDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /time-tables : get all the timeTables.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of timeTables in body
     */
    @GetMapping("/time-tables")
    public ResponseEntity<List<TimeTableDTO>> getAllTimeTables(Pageable pageable) {
        log.debug("REST request to get a page of TimeTables");
        Page<TimeTableDTO> page = timeTableService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/time-tables");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /time-tables/:id : get the "id" timeTable.
     *
     * @param id the id of the timeTableDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timeTableDTO, or with status 404 (Not Found)
     */
    @GetMapping("/time-tables/{id}")
    public ResponseEntity<TimeTableDTO> getTimeTable(@PathVariable Long id) {
        log.debug("REST request to get TimeTable : {}", id);
        Optional<TimeTableDTO> timeTableDTO = timeTableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(timeTableDTO);
    }

    /**
     * DELETE  /time-tables/:id : delete the "id" timeTable.
     *
     * @param id the id of the timeTableDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/time-tables/{id}")
    public ResponseEntity<Void> deleteTimeTable(@PathVariable Long id) {
        log.debug("REST request to delete TimeTable : {}", id);
        timeTableService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
