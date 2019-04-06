package com.lxisoft.sas.web.rest;
import com.lxisoft.sas.service.AssignmentService;
import com.lxisoft.sas.web.rest.errors.BadRequestAlertException;
import com.lxisoft.sas.web.rest.util.HeaderUtil;
import com.lxisoft.sas.web.rest.util.PaginationUtil;
import com.lxisoft.sas.service.dto.AssignmentDTO;
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
 * REST controller for managing Assignment.
 */
@RestController
@RequestMapping("/api")
public class AssignmentResource {

    private final Logger log = LoggerFactory.getLogger(AssignmentResource.class);

    private static final String ENTITY_NAME = "assignment";

    private final AssignmentService assignmentService;

    public AssignmentResource(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    /**
     * POST  /assignments : Create a new assignment.
     *
     * @param assignmentDTO the assignmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assignmentDTO, or with status 400 (Bad Request) if the assignment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assignments")
    public ResponseEntity<AssignmentDTO> createAssignment(@RequestBody AssignmentDTO assignmentDTO) throws URISyntaxException {
        log.debug("REST request to save Assignment : {}", assignmentDTO);
        if (assignmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new assignment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssignmentDTO result = assignmentService.save(assignmentDTO);
        return ResponseEntity.created(new URI("/api/assignments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assignments : Updates an existing assignment.
     *
     * @param assignmentDTO the assignmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assignmentDTO,
     * or with status 400 (Bad Request) if the assignmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the assignmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assignments")
    public ResponseEntity<AssignmentDTO> updateAssignment(@RequestBody AssignmentDTO assignmentDTO) throws URISyntaxException {
        log.debug("REST request to update Assignment : {}", assignmentDTO);
        if (assignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AssignmentDTO result = assignmentService.save(assignmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, assignmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assignments : get all the assignments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of assignments in body
     */
    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentDTO>> getAllAssignments(Pageable pageable) {
        log.debug("REST request to get a page of Assignments");
        Page<AssignmentDTO> page = assignmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assignments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /assignments/:id : get the "id" assignment.
     *
     * @param id the id of the assignmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assignmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/assignments/{id}")
    public ResponseEntity<AssignmentDTO> getAssignment(@PathVariable Long id) {
        log.debug("REST request to get Assignment : {}", id);
        Optional<AssignmentDTO> assignmentDTO = assignmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assignmentDTO);
    }

    /**
     * DELETE  /assignments/:id : delete the "id" assignment.
     *
     * @param id the id of the assignmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/assignments/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        log.debug("REST request to delete Assignment : {}", id);
        assignmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
