package com.lxisoft.sas.web.rest;
import com.lxisoft.sas.service.DayOfWeekService;
import com.lxisoft.sas.web.rest.errors.BadRequestAlertException;
import com.lxisoft.sas.web.rest.util.HeaderUtil;
import com.lxisoft.sas.web.rest.util.PaginationUtil;
import com.lxisoft.sas.service.dto.DayOfWeekDTO;
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
 * REST controller for managing DayOfWeek.
 */
@RestController
@RequestMapping("/api")
public class DayOfWeekResource {

    private final Logger log = LoggerFactory.getLogger(DayOfWeekResource.class);

    private static final String ENTITY_NAME = "dayOfWeek";

    private final DayOfWeekService dayOfWeekService;

    public DayOfWeekResource(DayOfWeekService dayOfWeekService) {
        this.dayOfWeekService = dayOfWeekService;
    }

    /**
     * POST  /day-of-weeks : Create a new dayOfWeek.
     *
     * @param dayOfWeekDTO the dayOfWeekDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dayOfWeekDTO, or with status 400 (Bad Request) if the dayOfWeek has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/day-of-weeks")
    public ResponseEntity<DayOfWeekDTO> createDayOfWeek(@RequestBody DayOfWeekDTO dayOfWeekDTO) throws URISyntaxException {
        log.debug("REST request to save DayOfWeek : {}", dayOfWeekDTO);
        if (dayOfWeekDTO.getId() != null) {
            throw new BadRequestAlertException("A new dayOfWeek cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DayOfWeekDTO result = dayOfWeekService.save(dayOfWeekDTO);
        return ResponseEntity.created(new URI("/api/day-of-weeks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /day-of-weeks : Updates an existing dayOfWeek.
     *
     * @param dayOfWeekDTO the dayOfWeekDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dayOfWeekDTO,
     * or with status 400 (Bad Request) if the dayOfWeekDTO is not valid,
     * or with status 500 (Internal Server Error) if the dayOfWeekDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/day-of-weeks")
    public ResponseEntity<DayOfWeekDTO> updateDayOfWeek(@RequestBody DayOfWeekDTO dayOfWeekDTO) throws URISyntaxException {
        log.debug("REST request to update DayOfWeek : {}", dayOfWeekDTO);
        if (dayOfWeekDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DayOfWeekDTO result = dayOfWeekService.save(dayOfWeekDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dayOfWeekDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /day-of-weeks : get all the dayOfWeeks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dayOfWeeks in body
     */
    @GetMapping("/day-of-weeks")
    public ResponseEntity<List<DayOfWeekDTO>> getAllDayOfWeeks(Pageable pageable) {
        log.debug("REST request to get a page of DayOfWeeks");
        Page<DayOfWeekDTO> page = dayOfWeekService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/day-of-weeks");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /day-of-weeks/:id : get the "id" dayOfWeek.
     *
     * @param id the id of the dayOfWeekDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dayOfWeekDTO, or with status 404 (Not Found)
     */
    @GetMapping("/day-of-weeks/{id}")
    public ResponseEntity<DayOfWeekDTO> getDayOfWeek(@PathVariable Long id) {
        log.debug("REST request to get DayOfWeek : {}", id);
        Optional<DayOfWeekDTO> dayOfWeekDTO = dayOfWeekService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dayOfWeekDTO);
    }

    /**
     * DELETE  /day-of-weeks/:id : delete the "id" dayOfWeek.
     *
     * @param id the id of the dayOfWeekDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/day-of-weeks/{id}")
    public ResponseEntity<Void> deleteDayOfWeek(@PathVariable Long id) {
        log.debug("REST request to delete DayOfWeek : {}", id);
        dayOfWeekService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
