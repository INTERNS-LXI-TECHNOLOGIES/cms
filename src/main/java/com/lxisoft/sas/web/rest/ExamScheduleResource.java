package com.lxisoft.sas.web.rest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lxisoft.sas.service.ExamScheduleService;
import com.lxisoft.sas.service.dto.ExamScheduleDTO;
import com.lxisoft.sas.web.rest.errors.BadRequestAlertException;
import com.lxisoft.sas.web.rest.util.HeaderUtil;
import com.lxisoft.sas.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing ExamSchedule.
 */
@RestController
@RequestMapping("/api")
public class ExamScheduleResource {

    private final Logger log = LoggerFactory.getLogger(ExamScheduleResource.class);

    private static final String ENTITY_NAME = "examSchedule";

    private final ExamScheduleService examScheduleService;

    public ExamScheduleResource(ExamScheduleService examScheduleService) {
        this.examScheduleService = examScheduleService;
    }

    /**
     * POST  /exam-schedules : Create a new examSchedule.
     *
     * @param examScheduleDTO the examScheduleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new examScheduleDTO, or with status 400 (Bad Request) if the examSchedule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exam-schedules")
    public ResponseEntity<ExamScheduleDTO> createExamSchedule(@RequestBody ExamScheduleDTO examScheduleDTO) throws URISyntaxException {
        log.debug("REST request to save ExamSchedule : {}", examScheduleDTO);
        if (examScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new examSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamScheduleDTO result = examScheduleService.save(examScheduleDTO);
        return ResponseEntity.created(new URI("/api/exam-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exam-schedules : Updates an existing examSchedule.
     *
     * @param examScheduleDTO the examScheduleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated examScheduleDTO,
     * or with status 400 (Bad Request) if the examScheduleDTO is not valid,
     * or with status 500 (Internal Server Error) if the examScheduleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exam-schedules")
    public ResponseEntity<ExamScheduleDTO> updateExamSchedule(@RequestBody ExamScheduleDTO examScheduleDTO) throws URISyntaxException {
        log.debug("REST request to update ExamSchedule : {}", examScheduleDTO);
        if (examScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExamScheduleDTO result = examScheduleService.save(examScheduleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, examScheduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exam-schedules : get all the examSchedules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of examSchedules in body
     */
    @GetMapping("/exam-schedules")
    public ResponseEntity<List<ExamScheduleDTO>> getAllExamSchedules(Pageable pageable) {
        log.debug("REST request to get a page of ExamSchedules");
        Page<ExamScheduleDTO> page = examScheduleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exam-schedules");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /exam-schedules/:id : get the "id" examSchedule.
     *
     * @param id the id of the examScheduleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the examScheduleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exam-schedules/{id}")
    public ResponseEntity<ExamScheduleDTO> getExamSchedule(@PathVariable Long id) {
        log.debug("REST request to get ExamSchedule : {}", id);
        Optional<ExamScheduleDTO> examScheduleDTO = examScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examScheduleDTO);
    }

    /**
     * DELETE  /exam-schedules/:id : delete the "id" examSchedule.
     *
     * @param id the id of the examScheduleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exam-schedules/{id}")
    public ResponseEntity<Void> deleteExamSchedule(@PathVariable Long id) {
        log.debug("REST request to delete ExamSchedule : {}", id);
        examScheduleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}