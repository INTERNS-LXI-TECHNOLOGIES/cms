package com.lxisoft.sas.web.rest;
import com.lxisoft.sas.service.ExamHallService;
import com.lxisoft.sas.web.rest.errors.BadRequestAlertException;
import com.lxisoft.sas.web.rest.util.HeaderUtil;
import com.lxisoft.sas.web.rest.util.PaginationUtil;
import com.lxisoft.sas.service.dto.ExamHallDTO;
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
 * REST controller for managing ExamHall.
 */
@RestController
@RequestMapping("/api")
public class ExamHallResource {

    private final Logger log = LoggerFactory.getLogger(ExamHallResource.class);

    private static final String ENTITY_NAME = "examHall";

    private final ExamHallService examHallService;

    public ExamHallResource(ExamHallService examHallService) {
        this.examHallService = examHallService;
    }

    /**
     * POST  /exam-halls : Create a new examHall.
     *
     * @param examHallDTO the examHallDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new examHallDTO, or with status 400 (Bad Request) if the examHall has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exam-halls")
    public ResponseEntity<ExamHallDTO> createExamHall(@RequestBody ExamHallDTO examHallDTO) throws URISyntaxException {
        log.debug("REST request to save ExamHall : {}", examHallDTO);
        if (examHallDTO.getId() != null) {
            throw new BadRequestAlertException("A new examHall cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamHallDTO result = examHallService.save(examHallDTO);
        return ResponseEntity.created(new URI("/api/exam-halls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exam-halls : Updates an existing examHall.
     *
     * @param examHallDTO the examHallDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated examHallDTO,
     * or with status 400 (Bad Request) if the examHallDTO is not valid,
     * or with status 500 (Internal Server Error) if the examHallDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exam-halls")
    public ResponseEntity<ExamHallDTO> updateExamHall(@RequestBody ExamHallDTO examHallDTO) throws URISyntaxException {
        log.debug("REST request to update ExamHall : {}", examHallDTO);
        if (examHallDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExamHallDTO result = examHallService.save(examHallDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, examHallDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exam-halls : get all the examHalls.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of examHalls in body
     */
    @GetMapping("/exam-halls")
    public ResponseEntity<List<ExamHallDTO>> getAllExamHalls(Pageable pageable) {
        log.debug("REST request to get a page of ExamHalls");
        Page<ExamHallDTO> page = examHallService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exam-halls");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /exam-halls/:id : get the "id" examHall.
     *
     * @param id the id of the examHallDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the examHallDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exam-halls/{id}")
    public ResponseEntity<ExamHallDTO> getExamHall(@PathVariable Long id) {
        log.debug("REST request to get ExamHall : {}", id);
        Optional<ExamHallDTO> examHallDTO = examHallService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examHallDTO);
    }

    /**
     * DELETE  /exam-halls/:id : delete the "id" examHall.
     *
     * @param id the id of the examHallDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exam-halls/{id}")
    public ResponseEntity<Void> deleteExamHall(@PathVariable Long id) {
        log.debug("REST request to delete ExamHall : {}", id);
        examHallService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
