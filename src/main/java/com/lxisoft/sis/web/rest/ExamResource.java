package com.lxisoft.sis.web.rest;
import com.lxisoft.sis.service.ExamService;
import com.lxisoft.sis.web.rest.errors.BadRequestAlertException;
import com.lxisoft.sis.web.rest.util.HeaderUtil;
import com.lxisoft.sis.web.rest.util.PaginationUtil;
import com.lxisoft.sis.service.dto.ExamDTO;
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
 * REST controller for managing Exam.
 */
@RestController
@RequestMapping("/api")
public class ExamResource {

    private final Logger log = LoggerFactory.getLogger(ExamResource.class);

    private static final String ENTITY_NAME = "exam";

    private final ExamService examService;

    public ExamResource(ExamService examService) {
        this.examService = examService;
    }

    /**
     * POST  /exams : Create a new exam.
     *
     * @param examDTO the examDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new examDTO, or with status 400 (Bad Request) if the exam has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exams")
    public ResponseEntity<ExamDTO> createExam(@RequestBody ExamDTO examDTO) throws URISyntaxException {
        log.debug("REST request to save Exam : {}", examDTO);
        if (examDTO.getId() != null) {
            throw new BadRequestAlertException("A new exam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamDTO result = examService.save(examDTO);
        return ResponseEntity.created(new URI("/api/exams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exams : Updates an existing exam.
     *
     * @param examDTO the examDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated examDTO,
     * or with status 400 (Bad Request) if the examDTO is not valid,
     * or with status 500 (Internal Server Error) if the examDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exams")
    public ResponseEntity<ExamDTO> updateExam(@RequestBody ExamDTO examDTO) throws URISyntaxException {
        log.debug("REST request to update Exam : {}", examDTO);
        if (examDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExamDTO result = examService.save(examDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, examDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exams : get all the exams.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of exams in body
     */
    @GetMapping("/exams")
    public ResponseEntity<List<ExamDTO>> getAllExams(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Exams");
        Page<ExamDTO> page;
        if (eagerload) {
            page = examService.findAllWithEagerRelationships(pageable);
        } else {
            page = examService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/exams?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /exams/:id : get the "id" exam.
     *
     * @param id the id of the examDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the examDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exams/{id}")
    public ResponseEntity<ExamDTO> getExam(@PathVariable Long id) {
        log.debug("REST request to get Exam : {}", id);
        Optional<ExamDTO> examDTO = examService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examDTO);
    }

    /**
     * DELETE  /exams/:id : delete the "id" exam.
     *
     * @param id the id of the examDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exams/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        log.debug("REST request to delete Exam : {}", id);
        examService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
